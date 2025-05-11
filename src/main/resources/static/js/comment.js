const originalValues = new Map();
export function toggleEdit(commentId) {
    const text = document.getElementById(`comment-text-${commentId}`);
    const form = document.getElementById(`comment-form-${commentId}`);
    const controls = document.getElementById(`comment-controls-${commentId}`);
    const contentSpan = text.querySelectorAll("p")[0];
    const authorSpan = text.querySelectorAll("p")[1];
    const originalContent = contentSpan.innerText.trim();
    const originalAuthor = authorSpan.innerText.trim();
    // 원래 값 저장
    originalValues.set(commentId, {
        content: originalContent,
        author: originalAuthor
    });
    text.style.display = 'none';
    form.style.display = 'flex';
    controls.style.display = 'none';
}
export function cancelEdit(commentId) {
    const text = document.getElementById(`comment-text-${commentId}`);
    const form = document.getElementById(`comment-form-${commentId}`);
    const controls = document.getElementById(`comment-controls-${commentId}`);
    // 저장했던 값 불러와서 복원
    const original = originalValues.get(commentId);
    if (original) {
        form.querySelector('textarea[name="content"]').value = original.content;
        form.querySelector('input[name="author"]').value = original.author;
    }
    text.style.display = 'flex';
    form.style.display = 'none';
    controls.style.display = 'flex';
}
export function validateComment(form) {
    const content = form.querySelector('textarea[name="content"]').value.trim();
    const author = form.querySelector('input[name="author"]').value.trim();
    if (!content || !author) {
        alert("댓글 내용과 작성자를 모두 입력해주세요!");
        return false;
    }
    return true;
}
export function showPasswordForm(action) {
    var _a;
    const form = document.getElementById('passwordForm');
    const postId = (_a = form.querySelector('input[name="postId"]')) === null || _a === void 0 ? void 0 : _a.value;
    form.action = `/posts/${postId}/verify-password?action=${action}`;
    form.style.display = "flex";
    const controls = document.getElementById("postButtons");
    controls.style.display = "none";
}
export function hidePostPasswordForm() {
    const form = document.getElementById("passwordForm");
    form.style.display = "none";
    const controls = document.getElementById("postButtons");
    controls.style.display = "flex";
}
export function cancelCommentPassword(commentId) {
    const form = document.getElementById(`comment-password-form-${commentId}`);
    form.style.display = 'none';
    const controls = document.getElementById(`comment-controls-${commentId}`);
    controls.style.display = 'flex';
}
export function togglePasswordForm(commentId, action) {
    const form = document.getElementById(`comment-password-form-${commentId}`);
    const postId = form.querySelector('input[name="postId"]').value;
    form.action = `/comments/${commentId}/verify-password?action=${action}&postId=${postId}`;
    form.style.display = 'flex';
    const controls = document.getElementById(`comment-controls-${commentId}`);
    if (controls)
        controls.style.display = 'none';
}
window.toggleEdit = toggleEdit;
window.cancelEdit = cancelEdit;
window.showPasswordForm = showPasswordForm;
window.hidePostPasswordForm = hidePostPasswordForm;
window.togglePasswordForm = togglePasswordForm;
window.validateComment = validateComment;
window.cancelCommentPassword = cancelCommentPassword;
