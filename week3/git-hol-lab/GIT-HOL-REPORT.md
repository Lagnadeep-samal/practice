# Git Hands-On Lab (Git-HOL) — Full Repo Simulation

**Project:** Git-HOL Demo App (a tiny static site used purely as a vehicle for Git practice)
**Focus:** Branching, merge conflicts, and a pull-request-style workflow

## 1. Setup

```bash
git init
git config user.email "om@example.com"
git config user.name "Om"
```

Scaffolded a minimal app: `index.html`, `style.css`, `script.js`.

```bash
git add .
git commit -m "Initial commit: scaffold demo app"
git branch -M main
```

## 2. Branching — two features off `main`

Two feature branches were created from `main`, each editing the *same* `<h1>` line on purpose, so they would later collide.

```bash
git checkout -b feature/header
# added a <nav> bar and an emoji to the <h1>
git commit -m "feature/header: add nav bar and emoji to title"

git checkout main
git checkout -b feature/footer
# added a footer and a checkmark to the *same* <h1> line
git commit -m "feature/footer: add footer and checkmark to title"
```

## 3. First merge (clean) — simulating PR #1

```bash
git checkout main
git merge --no-ff feature/header -m "Merge pull request: feature/header into main"
```

`--no-ff` is used deliberately so the merge shows up as its own commit in history, the way a "Merge pull request #N" commit looks on GitHub even when a fast-forward was possible.

## 4. Second merge (conflict) — simulating PR #2

```bash
git merge --no-ff feature/footer -m "Merge pull request: feature/footer into main"
```

Because both branches touched the `<h1>` line, Git stopped with:

```
Auto-merging index.html
CONFLICT (content): Merge conflict in index.html
```

The conflicted region looked like:

```html
<<<<<<< HEAD
    <nav>Home | About | Contact</nav>
    <h1>🚀 Git-HOL Demo App</h1>
=======
    <h1>Git-HOL Demo App ✅</h1>
>>>>>>> feature/footer
```

**Resolution:** kept both intended changes instead of picking one side —

```html
    <nav>Home | About | Contact</nav>
    <h1>🚀 Git-HOL Demo App ✅</h1>
```

Then:

```bash
git add index.html
git commit -m "Merge pull request: feature/footer into main (resolved conflict, combined nav + checkmark)"
```

## 5. A third branch — bugfix, simulating PR #3

```bash
git checkout -b bugfix/console-log-cleanup
# replaced console.log with console.info
git commit -m "bugfix: replace console.log with console.info"
git checkout main
git merge --no-ff bugfix/console-log-cleanup -m "Merge pull request: bugfix/console-log-cleanup into main"
```

## 6. Final history

```
*   0edd654 (HEAD -> main) Merge pull request: bugfix/console-log-cleanup into main
|\
| * 25a64ed (bugfix/console-log-cleanup) bugfix: replace console.log with console.info
|/
*   da24555 Merge pull request: feature/footer into main (resolved conflict, combined nav + checkmark)
|\
| * 372e99b (feature/footer) feature/footer: add footer and checkmark to title
* |   0d4eaa2 Merge pull request: feature/header into main
|\ \
| |/
|/|
| * 57fc16f (feature/header) feature/header: add nav bar and emoji to title
|/
* 370f426 Initial commit: scaffold demo app
```

## 7. Key Git commands practiced

| Command | Purpose |
|---|---|
| `git init` | Start a new repository |
| `git checkout -b <branch>` | Create and switch to a feature branch |
| `git add` / `git commit` | Stage and record changes |
| `git merge --no-ff` | Merge with an explicit merge commit (mirrors a GitHub PR merge) |
| Conflict markers (`<<<<<<<`, `=======`, `>>>>>>>`) | Manual conflict resolution |
| `git log --all --graph` | Visualize branch/merge history |

## 8. Simulating the "PR" part on GitHub

Since this repo was built locally, here's how each merge maps to a real pull-request workflow:

1. Push each feature branch: `git push -u origin feature/header`
2. Open a PR on GitHub from `feature/header` → `main`, review, and merge (this is exactly what `git merge --no-ff` reproduces locally)
3. For `feature/footer`, GitHub would flag the same conflict in the PR's "Resolve conflicts" UI — resolved the same way, then commit.
4. Delete feature branches after merge (`git branch -d feature/header`) to keep the repo tidy.

## Notes / Variations to try next

- Try `git rebase` instead of `merge --no-ff` on a new branch to compare a linear history vs. a merge-commit history.
- Practice `git cherry-pick` to bring the `bugfix` commit onto a hypothetical `release` branch.
- Practice `git revert` on the merge commit to see how reverting a merge differs from reverting a normal commit.
