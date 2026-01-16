import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
    selector: 'app-security-settings',
    standalone: true,
    imports: [CommonModule, FormsModule],
    templateUrl: './security.component.html',
    styles: [`
    .switch {
        position: relative;
        display: inline-block;
        width: 44px;
        height: 24px;
    }
    .switch input {
        opacity: 0;
        width: 0;
        height: 0;
    }
    .slider {
        position: absolute;
        cursor: pointer;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        background-color: #cbd5e1;
        transition: .4s;
        border-radius: 34px;
    }
    .slider:before {
        position: absolute;
        content: "";
        height: 18px;
        width: 18px;
        left: 3px;
        bottom: 3px;
        background-color: white;
        transition: .4s;
        border-radius: 50%;
    }
    input:checked + .slider {
        background-color: var(--primary-color, #204E4A);
    }
    input:checked + .slider:before {
        transform: translateX(20px);
    }
  `]
})
export class SecurityComponent {
    // Password Policy
    passwordLength = signal(12);
    passwordExpiry = signal('90');
    specialCharRequired = signal(true);

    // 2FA
    enable2FA = signal(true);
    defaultAuthMethod = signal('email'); // email, app, sso

    // Access Control
    ipWhitelist = signal('192.168.1.1\n113.190.23.45\n10.0.0.0/24');
    ipBlacklist = signal('45.227.253.12\n185.224.128.0/22');
    loginAttempts = signal(5);

    toggle2FA() {
        this.enable2FA.update(v => !v);
    }

    toggleSpecialChar() {
        this.specialCharRequired.update(v => !v);
    }

    setAuthMethod(method: string) {
        this.defaultAuthMethod.set(method);
    }

    reset() {
        if (confirm('Hủy bỏ các thay đổi chưa lưu?')) {
            // Reset logic here or reload
            console.log('Reset changes');
        }
    }

    save() {
        console.log('Saving security config...', {
            policy: {
                length: this.passwordLength(),
                expiry: this.passwordExpiry(),
                specialChar: this.specialCharRequired()
            },
            twoFactor: {
                enabled: this.enable2FA(),
                method: this.defaultAuthMethod()
            },
            access: {
                whitelist: this.ipWhitelist(),
                blacklist: this.ipBlacklist(),
                attempts: this.loginAttempts()
            }
        });
        alert('Đã cập nhật bảo mật thành công!');
    }
}
