# CiCdAngularDemo

This project was generated using [Angular CLI](https://github.com/angular/angular-cli) version 21.2.19.

## Development server

To start a local development server, run:

```bash
ng serve
```

Once the server is running, open your browser and navigate to `http://localhost:4200/`. The application will automatically reload whenever you modify any of the source files.

## Code scaffolding

Angular CLI includes powerful code scaffolding tools. To generate a new component, run:

```bash
ng generate component component-name
```

For a complete list of available schematics (such as `components`, `directives`, or `pipes`), run:

```bash
ng generate --help
```

## Building

To build the project run:

```bash
ng build
```

This will compile your project and store the build artifacts in the `dist/` directory. By default, the production build optimizes your application for performance and speed.

## Running unit tests

To execute unit tests with the [Vitest](https://vitest.dev/) test runner, use the following command:

```bash
ng test
```

## Running end-to-end tests

For end-to-end (e2e) testing, run:

```bash
ng e2e
```

Angular CLI does not come with an end-to-end testing framework by default. You can choose one that suits your needs.

## CI/CD Pipeline

This repo ships with a GitHub Actions workflow at `.github/workflows/ci-cd.yml` that runs automatically on every push/PR to `main` (and can also be triggered manually from the **Actions** tab).

It has three stages, each depending on the previous one succeeding:

1. **Test** — installs dependencies and runs the unit test suite (`npm test`, powered by Vitest — runs headlessly, no browser setup needed).
2. **Build** — runs `npm run build -- --configuration production` and uploads the resulting `dist/` folder as a workflow artifact.
3. **Deploy** — downloads that artifact and confirms it's ready to ship. This stage is currently a placeholder (no hosting target wired up) — see below to connect a real one.

To see it run:

```bash
git init
git add .
git commit -m "Initial commit with CI/CD pipeline"
git branch -M main
git remote add origin <your-repo-url>
git push -u origin main
```

Then open your repo's **Actions** tab on GitHub to watch the pipeline run.

### Wiring up a real deploy

Replace the last step of the `deploy` job in `ci-cd.yml` with one of these, depending on where you want it to go:

**GitHub Pages** (free, no secrets needed — just enable Pages in repo Settings → Pages → Source: GitHub Actions):
```yaml
      - name: Deploy to GitHub Pages
        uses: actions/deploy-pages@v4
```
(You'll also need to add the `pages: write` / `id-token: write` permissions and an `actions/configure-pages@v5` + `actions/upload-pages-artifact@v3` step before it — see the [official guide](https://github.com/actions/deploy-pages).)

**Docker Hub:**
```yaml
      - name: Build and push Docker image
        run: |
          docker build -t <your-dockerhub-username>/ci-cd-angular-demo:${{ github.sha }} .
          echo "${{ secrets.DOCKERHUB_TOKEN }}" | docker login -u <your-dockerhub-username> --password-stdin
          docker push <your-dockerhub-username>/ci-cd-angular-demo:${{ github.sha }}
```

**Render/Railway:** these platforms usually auto-deploy on push once you connect the GitHub repo in their dashboard — no workflow changes needed, though you can also trigger a deploy hook via `curl` in this step if you prefer.

## Additional Resources

For more information on using the Angular CLI, including detailed command references, visit the [Angular CLI Overview and Command Reference](https://angular.dev/tools/cli) page.
