import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
    selector: 'app-settings-layout',
    standalone: true,
    imports: [CommonModule, RouterModule],
    templateUrl: './settings-layout.component.html',
    styles: [`
    .settings-menu-active {
        @apply border-r-4 border-primary bg-slate-50 dark:bg-slate-800/50 text-primary font-bold;
    }
  `]
})
export class SettingsLayoutComponent {
    isDarkMode = signal(false);

    constructor() {
        // Sync initial state
        if (localStorage.getItem('theme') === 'dark' || document.documentElement.classList.contains('dark')) {
            this.isDarkMode.set(true);
        }
    }

    toggleDarkMode() {
        this.isDarkMode.update(v => !v);
        if (this.isDarkMode()) {
            document.documentElement.classList.add('dark');
            localStorage.setItem('theme', 'dark');
        } else {
            document.documentElement.classList.remove('dark');
            localStorage.setItem('theme', 'light');
        }
    }
}
