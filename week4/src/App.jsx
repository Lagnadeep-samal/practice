import { useState, useMemo } from 'react'
import TodoForm from './components/TodoForm.jsx'
import TodoList from './components/TodoList.jsx'
import FilterBar from './components/FilterBar.jsx'
import './App.css'

let nextId = 1

function App() {
  const [todos, setTodos] = useState([])
  const [filter, setFilter] = useState('all')

  // CREATE
  function addTodo(text) {
    setTodos((prev) => [...prev, { id: nextId++, text, done: false }])
  }

  // UPDATE (toggle done)
  function toggleTodo(id) {
    setTodos((prev) =>
      prev.map((t) => (t.id === id ? { ...t, done: !t.done } : t)),
    )
  }

  // UPDATE (edit text)
  function editTodo(id, newText) {
    setTodos((prev) =>
      prev.map((t) => (t.id === id ? { ...t, text: newText } : t)),
    )
  }

  // DELETE
  function deleteTodo(id) {
    setTodos((prev) => prev.filter((t) => t.id !== id))
  }

  const filteredTodos = useMemo(() => {
    if (filter === 'active') return todos.filter((t) => !t.done)
    if (filter === 'done') return todos.filter((t) => t.done)
    return todos
  }, [todos, filter])

  const counts = useMemo(
    () => ({
      all: todos.length,
      active: todos.filter((t) => !t.done).length,
      done: todos.filter((t) => t.done).length,
    }),
    [todos],
  )

  return (
    <div className="app">
      <h1>React Todo — CRUD Hands-On</h1>
      <TodoForm onAdd={addTodo} />
      <FilterBar filter={filter} onChange={setFilter} counts={counts} />
      <TodoList
        todos={filteredTodos}
        onToggle={toggleTodo}
        onDelete={deleteTodo}
        onEdit={editTodo}
      />
    </div>
  )
}

export default App
