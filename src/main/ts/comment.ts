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

declare global {
  interface Window {
    toggleEdit: (id: number) => void;
    cancelEdit: (id: number) => void;
    validateComment: (form: HTMLFormElement) => boolean;
  }
}

window.toggleEdit = toggleEdit;
window.cancelEdit = cancelEdit;
window.validateComment = validateComment;