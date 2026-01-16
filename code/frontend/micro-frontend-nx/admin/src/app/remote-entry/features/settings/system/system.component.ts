import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
    selector: 'app-system-config',
    standalone: true,
    imports: [CommonModule, FormsModule],
    templateUrl: './system.component.html',
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
    .slider-toggle {
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
    .slider-toggle:before {
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
    input:checked + .slider-toggle {
        background-color: var(--primary-color, #204E4A);
    }
    input:checked + .slider-toggle:before {
        transform: translateX(20px);
    }
  `]
})
export class SystemComponent {
    // Form Models
    systemName = signal('Hệ thống quản trị Admin Hub 2026');
    timezone = signal('GMT+7');
    language = signal('vi');
    dateFormat = signal('DD/MM/YYYY');

    // Environment
    envMode = signal('PROD');
    debugMode = signal(false);
    timeout = signal(3000);
    requestLimit = signal(120);

    // Cache
    useRedis = signal(true);
    ttl = signal(3600);

    toggleDebugMode() {
        this.debugMode.update(v => !v);
    }

    toggleRedis() {
        this.useRedis.update(v => !v);
    }

    save() {
        console.log('Saving system config...', {
            systemName: this.systemName(),
            timezone: this.timezone(),
            language: this.language(),
            dateFormat: this.dateFormat(),
            envMode: this.envMode(),
            debugMode: this.debugMode(),
            timeout: this.timeout(),
            requestLimit: this.requestLimit(),
            useRedis: this.useRedis(),
            ttl: this.ttl()
        });
        alert('Đã lưu thay đổi hệ thống!');
    }

    reset() {
        if (confirm('Bạn có chắc muốn khôi phục mặc định?')) {
            this.systemName.set('Hệ thống quản trị Admin Hub 2026');
            this.timezone.set('GMT+7');
            this.language.set('vi');
            this.dateFormat.set('DD/MM/YYYY');
            this.envMode.set('PROD');
            this.debugMode.set(false);
            this.timeout.set(3000);
            this.requestLimit.set(120);
            this.useRedis.set(true);
            this.ttl.set(3600);
        }
    }
}
