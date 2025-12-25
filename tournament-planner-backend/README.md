Find logs in devcontainer in `/root/local/logs`

## Running Integration Tests

Integration tests use [Testcontainers](https://testcontainers.com/) to spin up a PostgreSQL database. By default, Testcontainers expects Docker, but you can also use Podman.

### Using Docker

No additional configuration needed. Just run:

```bash
mvn test
```

### Using Podman

To use Podman instead of Docker, you need to:

1. **Enable the Podman socket**

   ```bash
   systemctl --user enable podman.socket
   systemctl --user start podman.socket
   ```

2. **Create `~/.testcontainers.properties`** with the following content:

   ```properties
   docker.host=unix:///run/user/<your-uid>/podman/podman.sock
   ```

   Replace `<your-uid>` with your user ID (run `id -u` to find it). For example:

   ```properties
   docker.host=unix:///run/user/1000/podman/podman.sock
   ```

3. **Run the tests**

   ```bash
   mvn test
   ```

#### Troubleshooting

- **Socket not found**: Ensure the Podman socket is running:
  ```bash
  systemctl --user status podman.socket
  ```

- **Permission denied**: Check socket permissions:
  ```bash
  ls -la /run/user/$(id -u)/podman/podman.sock
  ```

- **Alternative: Environment variable**: Instead of the properties file, you can set:
  ```bash
  export DOCKER_HOST=unix:///run/user/$(id -u)/podman/podman.sock
  ```