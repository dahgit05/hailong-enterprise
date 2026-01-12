import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
    selector: 'app-reset-password-dialog',
    standalone: true,
    imports: [CommonModule],
    templateUrl: './reset-password-dialog.component.html',
    styleUrls: ['./reset-password-dialog.component.css']
})
export class ResetPasswordDialogComponent {
    @Input() isOpen = false;
    @Input() user: any = null;
    @Output() close = new EventEmitter<void>();
    @Output() confirm = new EventEmitter<string>(); // Emit new password

    newPassword = '';
    confirmPassword = '';
    showPassword = false;
    showConfirmPassword = false;

    onClose() {
        this.close.emit();
        this.resetForm();
    }

    onConfirm() {
        // Add validation logic here if needed
        if (this.newPassword && this.newPassword === this.confirmPassword) {
            this.confirm.emit(this.newPassword);
            this.onClose();
        } else {
            alert('Mật khẩu không khớp hoặc để trống!');
        }
    }

    toggleShowPassword() {
        this.showPassword = !this.showPassword;
    }

    toggleShowConfirmPassword() {
        this.showConfirmPassword = !this.showConfirmPassword;
    }

    // Helper to bind input (manual binding since generic input event)
    updateNewPassword(event: any) {
        this.newPassword = event.target.value;
    }

    updateConfirmPassword(event: any) {
        this.confirmPassword = event.target.value;
    }

    private resetForm() {
        this.newPassword = '';
        this.confirmPassword = '';
        this.showPassword = false;
        this.showConfirmPassword = false;
    }
}
