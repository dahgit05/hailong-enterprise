import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
    selector: 'app-notification-settings',
    standalone: true,
    imports: [CommonModule, FormsModule],
    templateUrl: './notification.component.html',
    styles: [`
    .switch {
        position: relative;
        display: inline-block;
        width: 34px;
        height: 18px;
    }
    .switch input { 
        opacity: 0;
        width: 0;
        height: 0;
    }
    .slider-toggle {
        position: absolute;
        cursor: pointer;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        background-color: #e2e8f0;
        transition: .3s cubic-bezier(0.4, 0, 0.2, 1);
        border-radius: 20px;
    }
    .slider-toggle:before {
        position: absolute;
        content: "";
        height: 12px;
        width: 12px;
        left: 3px;
        bottom: 3px;
        background-color: white;
        transition: .3s cubic-bezier(0.4, 0, 0.2, 1);
        border-radius: 50%;
        box-shadow: 0 1px 2px rgba(0,0,0,0.1);
    }
    input:checked + .slider-toggle {
        background-color: var(--primary-color, #204E4A);
    }
    input:checked + .slider-toggle:before {
        transform: translateX(16px);
    }
    .ultra-thin-input {
        @apply border-[0.5px] border-slate-200 dark:border-slate-700 bg-white dark:bg-slate-900 rounded-lg text-sm transition-all duration-200 outline-none;
        box-shadow: inset 0 1px 2px rgba(0, 0, 0, 0.02);
    }
    .ultra-thin-input:focus {
        @apply border-primary/40 ring-0 ring-offset-0;
        box-shadow: inset 0 1px 2px rgba(0, 0, 0, 0.02), 0 0 0 3px rgba(32, 78, 74, 0.05);
    }
    .pastel-blue { @apply bg-blue-50 text-blue-600 dark:bg-blue-900/20 dark:text-blue-400; }
    .pastel-amber { @apply bg-amber-50 text-amber-600 dark:bg-amber-900/20 dark:text-amber-400; }
    .shadow-premium {
        box-shadow: 0 10px 30px -12px rgba(32, 78, 74, 0.15);
    }
  `]
})
export class NotificationComponent {
    // Channels
    emailEnabled = signal(true);
    smsEnabled = signal(false);
    pushEnabled = signal(true);

    // SMTP Config
    smtpHost = signal('smtp.hailong.com.vn');
    smtpPort = signal(587);
    smtpUser = signal('no-reply@hailong.com.vn');
    smtpPassword = signal('••••••••••••');
    showSmtpPassword = signal(false);

    toggleEmail() {
        this.emailEnabled.update(v => !v);
    }

    toggleSMS() {
        this.smsEnabled.update(v => !v);
    }

    togglePush() {
        this.pushEnabled.update(v => !v);
    }

    toggleSmtpPasswordVisibility() {
        this.showSmtpPassword.update(v => !v);
    }

    reset() {
        if (confirm('Hủy bỏ các thay đổi chưa lưu?')) {
            console.log('Reset notification config');
        }
    }

    save() {
        console.log('Saving notification config...', {
            channels: {
                email: this.emailEnabled(),
                sms: this.smsEnabled(),
                push: this.pushEnabled()
            },
            smtp: {
                host: this.smtpHost(),
                port: this.smtpPort(),
                user: this.smtpUser(),
            }
        });
        alert('Đã cập nhật cấu hình thông báo!');
    }
}
