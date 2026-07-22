# React Todo — CRUD Hands-On Lab

A small React app demonstrating full CRUD (Create, Read, Update, Delete) using
component state and props — no backend required.

## Structure

```
src/
  App.jsx              main state + logic (todos array, filter)
  App.css              styling
  index.css            base/global styles
  main.jsx             React root mount
  components/
    TodoForm.jsx        Create — input + add button
    TodoList.jsx         Read — renders the filtered list
    TodoItem.jsx        Update/Delete — checkbox, edit, delete per row
    FilterBar.jsx        Read — All / Active / Done tabs
```

## How the CRUD maps to code

- **Create**: `TodoForm` collects text in local state, calls `onAdd(text)` on submit → `App.addTodo`.
- **Read**: `App` derives `filteredTodos` with `useMemo` based on the `filter` state, and `TodoList` renders them.
- **Update**: `TodoItem` toggles `done` via checkbox (`onToggle`), or double-click / Edit button switches to an inline text input that saves on blur or Enter (`onEdit`).
- **Delete**: `TodoItem`'s Delete button calls `onDelete(id)` → `App.deleteTodo` filters it out.

## Run it

```bash
npm install
npm run dev
```

Then open the printed local URL (usually http://localhost:5173).

## Build for production

```bash
npm run build
npm run preview
```

## Ideas to extend (good next hands-on steps)

- Persist todos with `localStorage` (read on load, write on every change).
- Add due dates and sort by them.
- Add drag-to-reorder.
- Swap local state for a `useReducer` to practice reducer patterns.
