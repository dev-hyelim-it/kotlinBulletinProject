const originalValues = new Map<number, { content: string; author: string }>();

export function toggleEdit(commentId: number): void {
    const text = document.getElementById(`comment-text-${commentId}`) as HTMLElement;
    const form = document.getElementById(`comment-form-${commentId}`) as HTMLFormElement;
    const controls = document.getElementById(`comment-controls-${commentId}`) as HTMLElement;

    const contentSpan = text.querySelectorAll("p")[0] as HTMLElement;
    const authorSpan = text.querySelectorAll("p")[1] as HTMLElement;

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

export function cancelEdit(commentId: number): void {
    const text = document.getElementById(`comment-text-${commentId}`) as HTMLElement;
    const form = document.getElementById(`comment-form-${commentId}`) as HTMLFormElement;
    const controls = document.getElementById(`comment-controls-${commentId}`) as HTMLElement;

    // 저장했던 값 불러와서 복원
    const original = originalValues.get(commentId);
    if (original) {
        (form.querySelector('textarea[name="content"]') as HTMLInputElement).value = original.content;
        (form.querySelector('input[name="author"]') as HTMLInputElement).value = original.author;
    }

    text.style.display = 'flex';
    form.style.display = 'none';
    controls.style.display = 'flex';
}

export function validateComment(form: HTMLFormElement): boolean {
    const content = (form.querySelector('textarea[name="content"]') as HTMLInputElement).value.trim();
    const author = (form.querySelector('input[name="author"]') as HTMLInputElement).value.trim();

    if (!content || !author) {
        alert("댓글 내용과 작성자를 모두 입력해주세요!");
        return false;
    }

    return true;
}

export function showPasswordForm(action: 'edit' | 'delete'): void {
    const form = document.getElementById('passwordForm') as HTMLFormElement;
    const postId = (form.querySelector('input[name="postId"]') as HTMLInputElement)?.value;

    form.action = `/posts/${postId}/verify-password?action=${action}`;
    form.style.display = "flex";

    const controls = document.getElementById("postButtons") as HTMLElement;
    controls.style.display = "none";
}

export function hidePostPasswordForm(): void {
    const form = document.getElementById("passwordForm") as HTMLElement;
    form.style.display = "none";

    const controls = document.getElementById("postButtons") as HTMLElement;
    controls.style.display = "flex";
}

export function cancelCommentPassword(commentId: number): void {
    const form = document.getElementById(`comment-password-form-${commentId}`) as HTMLElement;
    form.style.display = 'none';

    const controls = document.getElementById(`comment-controls-${commentId}`) as HTMLElement;
    controls.style.display = 'flex';
}

export function togglePasswordForm(commentId: number, action: 'edit' | 'delete'): void {
    const form = document.getElementById(`comment-password-form-${commentId}`) as HTMLFormElement;
    const postId = (form.querySelector('input[name="postId"]') as HTMLInputElement).value;

    form.action = `/comments/${commentId}/verify-password?action=${action}&postId=${postId}`;
    form.style.display = 'flex';

    const controls = document.getElementById(`comment-controls-${commentId}`) as HTMLElement;
    if (controls) controls.style.display = 'none';
}


declare global {
  interface Window {
    toggleEdit: (id: number) => void;
    cancelEdit: (id: number) => void;
    showPasswordForm: (action: 'edit' | 'delete') => void;
    hidePostPasswordForm: (id: number) => void;
    togglePasswordForm: (id: number, action: 'edit' | 'delete') => void;
    validateComment: (form: HTMLFormElement) => boolean;
    cancelCommentPassword: (id: number) => void;
  }
}

window.toggleEdit = toggleEdit;
window.cancelEdit = cancelEdit;
window.showPasswordForm = showPasswordForm;
window.hidePostPasswordForm = hidePostPasswordForm;
window.togglePasswordForm = togglePasswordForm;
window.validateComment = validateComment;
window.cancelCommentPassword = cancelCommentPassword;
