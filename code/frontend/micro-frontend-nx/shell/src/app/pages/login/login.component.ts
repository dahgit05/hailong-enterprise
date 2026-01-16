import { Component, signal, effect } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';

@Component({
    selector: 'app-login',
    standalone: true,
    imports: [CommonModule, RouterModule],
    templateUrl: './login.component.html',
    styles: [`
        :host {
            display: block;
        }
        .material-symbols-outlined {
            font-variation-settings: 'FILL' 0, 'wght' 300, 'GRAD' 0, 'opsz' 20;
        }
        .glass-card {
            background: rgba(255, 255, 255, 0.7);
            backdrop-filter: blur(30px);
            border: 1px solid rgba(32, 78, 74, 0.15);
            box-shadow: 0 20px 50px rgba(32, 78, 74, 0.05), inset 0 0 2px rgba(255, 255, 255, 0.8);
        }
        :host-context(.dark) .glass-card {
            background: rgba(15, 23, 42, 0.7);
            border: 1px solid rgba(255, 255, 255, 0.1);
            box-shadow: 0 20px 50px rgba(0, 0, 0, 0.3), inset 0 0 2px rgba(255, 255, 255, 0.05);
        }
        .architectural-grid {
            background-image:
                linear-gradient(to right, rgba(32, 78, 74, 0.03) 1px, transparent 1px),
                linear-gradient(to bottom, rgba(32, 78, 74, 0.03) 1px, transparent 1px);
            background-size: 60px 60px;
        }
        :host-context(.dark) .architectural-grid {
             background-image:
                linear-gradient(to right, rgba(255, 255, 255, 0.03) 1px, transparent 1px),
                linear-gradient(to bottom, rgba(255, 255, 255, 0.03) 1px, transparent 1px);
        }
        .glow-button {
            box-shadow: 0 0 15px rgba(32, 78, 74, 0.2);
        }
    `]
})
export class LoginComponent {
    activeTab = signal<'password' | 'qr'>('password');
    showPassword = signal<boolean>(false);
    isDarkMode = signal<boolean>(false);

    constructor(private router: Router) {
        // Init logic theme if needed, but styling is mostly handled by parent or local classes
        const savedTheme = localStorage.getItem('theme');
        if (savedTheme === 'dark') {
            this.isDarkMode.set(true);
            document.documentElement.classList.add('dark');
        }
    }

    toggleTab(tab: 'password' | 'qr'): void {
        this.activeTab.set(tab);
    }

    togglePasswordVisibility(): void {
        this.showPassword.update(v => !v);
    }

    toggleTheme(): void {
        this.isDarkMode.update(v => !v);
        if (this.isDarkMode()) {
            document.documentElement.classList.add('dark');
            localStorage.setItem('theme', 'dark');
        } else {
            document.documentElement.classList.remove('dark');
            localStorage.setItem('theme', 'light');
        }
    }

    login(): void {
        console.log('Logging in...');
        // Mock delay
        setTimeout(() => {
            this.router.navigate(['/admin']);
        }, 500);
    }

    loginWithGoogle(): void {
        console.log('Login with Google clicked');
    }
}
