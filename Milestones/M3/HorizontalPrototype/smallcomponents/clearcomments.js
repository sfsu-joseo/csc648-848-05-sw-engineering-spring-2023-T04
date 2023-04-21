import React, { useState } from "react";

function CommentsPanel() {
  const [comments, setComments] = useState([]);

  const handleClearComments = () => {
    setComments([]);
  };

  const handleAddComment = (event) => {
    event.preventDefault();
    const newComment = event.target.comment.value;
    setComments([...comments, newComment]);
    event.target.comment.value = "";
  };

  return (
    <div>
      <form onSubmit={handleAddComment}>
        <label>
          Add comment:
          <input type="text" name="comment" />
        </label>
        <button type="submit">Submit</button>
      </form>
      {comments.length > 0 && (
        <div>
          <h2>Comments:</h2>
          <ul>
            {comments.map((comment, index) => (
              <li key={index}>{comment}</li>
            ))}
          </ul>
          <label>
            Clear comments:
            <select onChange={handleClearComments}>
              <option value="">Select an option</option>
              <option value="clear">Clear</option>
            </select>
          </label>
        </div>
      )}
    </div>
  );
}

export default CommentsPanel;
