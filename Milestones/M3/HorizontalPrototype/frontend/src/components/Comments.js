const Comments = () => {
    return(
        <div class="commentsContainer">
            <ul class="commentList">
                <li>
                <div class="commenterImage">
                    <img src="http://placekitten.com/50/50" />
                </div>

                <div class="commentText">
                    <p class=""> <span class="commenterUsername">{"@username: "}</span> Hello this is a test comment what happens if this one gets too long.</p> <span class="date sub-text">on March 5th, 2014</span>
                </div>
                </li>
            </ul>
        </div>
    );
}

export default Comments;