import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
    selector: 'app-forbidden',
    standalone: true,
    imports: [CommonModule, RouterModule],
    templateUrl: './forbidden.component.html',
    styles: [`
    :host {
      display: block;
      height: 100%;
    }
    .custom-scrollbar::-webkit-scrollbar { width: 6px; }
    .custom-scrollbar::-webkit-scrollbar-track { background: transparent; }
    .custom-scrollbar::-webkit-scrollbar-thumb { background: #cbd5e1; border-radius: 10px; }
    :host-context(.dark) .custom-scrollbar::-webkit-scrollbar-thumb { background: #334155; }
    .glow-effect {
        box-shadow: 0 0 40px rgba(32, 78, 74, 0.15);
    }
    :host-context(.dark) .glow-effect {
        box-shadow: 0 0 40px rgba(16, 185, 129, 0.1);
    }
    .high-tech-ring {
        border: 2px dashed rgba(32, 78, 74, 0.2);
        animation: spin 60s linear infinite;
    }
    @keyframes spin {
        from { transform: rotate(0deg); }
        to { transform: rotate(360deg); }
    }
    .material-symbols-outlined {
        font-variation-settings: 'FILL' 0, 'wght' 200, 'GRAD' 0, 'opsz' 48;
    }
  `]
})
export class ForbiddenComponent {
    toggleTheme() {
        const html = document.documentElement;
        if (html.classList.contains('dark')) {
            html.classList.remove('dark');
            localStorage.setItem('theme', 'light');
        } else {
            html.classList.add('dark');
            localStorage.setItem('theme', 'dark');
        }
    }

    /** Request access - placeholder */
    requestAccess(): void {
        console.log('Request access clicked - Feature coming soon');
        // TODO: Implement access request functionality
    }
}
