const Notes = ({notesData, setNotesData}) => {
    return(
        <textarea id="notesText" 
            type="text"
            value={notesData}
            placeholder="write notes here..."
            onChange={(e) => setNotesData(e.target.value)}/>
    );
}

export default Notes;