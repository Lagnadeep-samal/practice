const FILTERS = ['all', 'active', 'done']

function FilterBar({ filter, onChange, counts }) {
  return (
    <div className="filter-bar">
      {FILTERS.map((f) => (
        <button
          key={f}
          className={filter === f ? 'active-filter' : ''}
          onClick={() => onChange(f)}
        >
          {f[0].toUpperCase() + f.slice(1)} ({counts[f]})
        </button>
      ))}
    </div>
  )
}

export default FilterBar
