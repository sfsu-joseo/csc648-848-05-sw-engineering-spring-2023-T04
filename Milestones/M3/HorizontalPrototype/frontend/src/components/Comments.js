const Comments = ({commentsData}) => {
    const commentItems = commentsData.map(comment =>
                <li key={comment.id}>
                <div class="commenterImage">
                    <img src={comment.commenterImage} />
                </div>
                <div class="commentText">
                    <p class=""> <span class="commenterUsername">@{comment.username}:</span> {comment.commentText}</p> <span class="date sub-text">on {comment.date}</span>
                </div>
                </li>

    );
    return(
        <div class="commentsContainer">
            <ul class="commentList">
                {commentItems}
            </ul>
        </div>
    );
}

export default Comments;