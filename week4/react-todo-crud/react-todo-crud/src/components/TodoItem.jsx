import { useState } from 'react'

// UPDATE + DELETE: one row in the list
function TodoItem({ todo, onToggle, onDelete, onEdit }) {
  const [isEditing, setIsEditing] = useState(false)
  const [draft, setDraft] = useState(todo.text)

  function saveEdit() {
    const trimmed = draft.trim()
    if (trimmed) {
      onEdit(todo.id, trimmed)
    } else {
      setDraft(todo.text) // don't allow saving empty text
    }
    setIsEditing(false)
  }

  return (
    <li className={`todo-item ${todo.done ? 'done' : ''}`}>
      <input
        type="checkbox"
        checked={todo.done}
        onChange={() => onToggle(todo.id)}
        aria-label={`Mark "${todo.text}" as ${todo.done ? 'active' : 'done'}`}
      />

      {isEditing ? (
        <input
          type="text"
          className="edit-input"
          value={draft}
          autoFocus
          onChange={(e) => setDraft(e.target.value)}
          onBlur={saveEdit}
          onKeyDown={(e) => {
            if (e.key === 'Enter') saveEdit()
            if (e.key === 'Escape') {
              setDraft(todo.text)
              setIsEditing(false)
            }
          }}
        />
      ) : (
        <span className="todo-text" onDoubleClick={() => setIsEditing(true)}>
          {todo.text}
        </span>
      )}

      <div className="todo-actions">
        <button onClick={() => setIsEditing(true)} aria-label="Edit">
          Edit
        </button>
        <button onClick={() => onDelete(todo.id)} aria-label="Delete">
          Delete
        </button>
      </div>
    </li>
  )
}

export default TodoItem
