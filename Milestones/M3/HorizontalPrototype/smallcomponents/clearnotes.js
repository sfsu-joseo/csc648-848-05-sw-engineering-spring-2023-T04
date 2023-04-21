import React, { useState } from "react";

function NotesPanel() {
  const [notes, setNotes] = useState([]);

  const handleClearNotes = () => {
    setNotes([]);
  };

  const handleAddNotes = (event) => {
    event.preventDefault();
    const newNotes = event.target.notes.value;
    setNotes([...notes, newNotes]);
    event.target.notes.value = "";
  };

  return (
    <div>
      <form onSubmit={handleAddNotes}>
        <label>
          Add notes:
          <input type="text" name="notes" />
        </label>
        <button type="submit">Submit</button>
      </form>
      {notes.length > 0 && (
        <div>
          <h2>Notes:</h2>
          <ul>
            {notes.map((notes, index) => (
              <li key={index}>{notes}</li>
            ))}
          </ul>
          <label>
            Clear notes:
            <select onChange={handleClearNotes}>
              <option value="">Select an option</option>
              <option value="clear">Clear</option>
            </select>
          </label>
        </div>
      )}
    </div>
  );
}

export default NotesPanel;
