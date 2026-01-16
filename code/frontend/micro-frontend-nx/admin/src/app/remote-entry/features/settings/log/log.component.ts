import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
    selector: 'app-log-settings',
    standalone: true,
    imports: [CommonModule, FormsModule],
    templateUrl: './log.component.html',
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
    input[type="range"] {
        -webkit-appearance: none;
        width: 100%;
        height: 6px;
        background: #e2e8f0;
        border-radius: 5px;
        outline: none;
    }
    input[type="range"]::-webkit-slider-thumb {
        -webkit-appearance: none;
        appearance: none;
        width: 18px;
        height: 18px;
        background: var(--primary-color, #204E4A);
        cursor: pointer;
        border-radius: 50%;
    }
    .segmented-control {
        @apply flex p-1 bg-slate-100 dark:bg-slate-800 rounded-xl w-full;
    }
    .segmented-control button {
        @apply flex-1 py-2 text-xs font-bold rounded-lg transition-all;
    }
    .segmented-control button.active {
        @apply bg-white dark:bg-slate-700 text-primary dark:text-emerald-500 shadow-sm;
    }
    .segmented-control button:not(.active) {
        @apply text-slate-500 hover:text-slate-700 dark:hover:text-slate-300;
    }
  `]
})
export class LogComponent {
    // Retention Policy
    retentionPeriod = signal('180');
    autoCleanup = signal(true);

    // Logging Level
    logLevel = signal('DEBUG');

    // Resource Monitoring
    sampleRate = signal(5);
    apiPerfLog = signal(true);

    toggleAutoCleanup() {
        this.autoCleanup.update(v => !v);
    }

    setLogLevel(level: string) {
        this.logLevel.set(level);
    }

    toggleApiPerfLog() {
        this.apiPerfLog.update(v => !v);
    }

    reset() {
        if (confirm('Khôi phục cấu hình nhật ký mặc định?')) {
            this.retentionPeriod.set('180');
            this.autoCleanup.set(true);
            this.logLevel.set('DEBUG');
            this.sampleRate.set(5);
            this.apiPerfLog.set(true);
        }
    }

    save() {
        console.log('Saving log config...', {
            retention: {
                period: this.retentionPeriod(),
                autoCleanup: this.autoCleanup()
            },
            level: this.logLevel(),
            monitoring: {
                sampleRate: this.sampleRate(),
                apiLog: this.apiPerfLog()
            }
        });
        alert('Đã cập nhật cấu hình nhật ký hệ thống!');
    }
}
