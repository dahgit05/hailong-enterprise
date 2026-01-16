import { Component, EventEmitter, Output, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
    selector: 'app-change-password-dialog',
    standalone: true,
    imports: [CommonModule, FormsModule],
    templateUrl: './change-password-dialog.component.html',
})
export class ChangePasswordDialogComponent {
    @Output() close = new EventEmitter<void>();

    currentPassword = '';
    newPassword = '';
    confirmPassword = '';

    showCurrentPassword = signal(false);
    showNewPassword = signal(false);
    showConfirmPassword = signal(false);

    // Strength calculation logic (simplified)
    get passwordStrength(): number {
        let score = 0;
        if (this.newPassword.length >= 8) score++;
        if (/[A-Z]/.test(this.newPassword)) score++;
        if (/[0-9]/.test(this.newPassword)) score++;
        if (/[^A-Za-z0-9]/.test(this.newPassword)) score++;
        return score;
    }

    get strengthLabel(): string {
        const s = this.passwordStrength;
        if (s <= 1) return 'Yếu';
        if (s === 2) return 'Trung bình';
        if (s === 3) return 'Khá';
        return 'Mạnh';
    }

    get strengthColorClass(): string {
        const s = this.passwordStrength;
        if (s <= 1) return 'bg-red-500';
        if (s === 2) return 'bg-yellow-500';
        if (s === 3) return 'bg-blue-500';
        return 'bg-green-500';
    }

    get strengthTextClass(): string {
        const s = this.passwordStrength;
        if (s <= 1) return 'text-red-500';
        if (s === 2) return 'text-yellow-500';
        if (s === 3) return 'text-blue-500';
        return 'text-green-500';
    }

    toggleCurrentPassword() {
        this.showCurrentPassword.update(v => !v);
    }

    toggleNewPassword() {
        this.showNewPassword.update(v => !v);
    }

    toggleConfirmPassword() {
        this.showConfirmPassword.update(v => !v);
    }

    onSubmit() {
        // Validate
        if (this.newPassword !== this.confirmPassword) {
            alert('Mật khẩu xác nhận không khớp!');
            return;
        }
        console.log('Updating password...');
        // Emit close or success logic
        this.close.emit();
    }

    onCancel() {
        this.close.emit();
    }
}
