import { Component, signal, effect } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

interface NavItem {
    icon: string;
    route: string;
    tooltip: string;
}

@Component({
    selector: 'app-admin-sidebar',
    standalone: true,
    imports: [CommonModule, RouterModule],
    templateUrl: './sidebar.component.html',
})
export class AdminSidebarComponent {
    /** Sidebar toggle state */
    isExpanded = signal<boolean>(true);

    /** Navigation items matching user snippet */
    navItems: NavItem[] = [
        { icon: 'apps', route: '/admin', tooltip: 'Hệ thống' },
        { icon: 'settings', route: '/admin/config', tooltip: 'Cấu hình' },
        { icon: 'history', route: '/admin/logs', tooltip: 'Nhật ký' },
        { icon: 'shield_person', route: '/admin/security', tooltip: 'Bảo mật' },
    ];

    constructor() {
        // Restore state from localStorage
        const savedState = localStorage.getItem('sidebarExpanded');
        if (savedState !== null) {
            this.isExpanded.set(savedState === 'true');
        }

        // Persist state
        effect(() => {
            localStorage.setItem('sidebarExpanded', String(this.isExpanded()));
        });
    }

    /** Toggle sidebar */
    toggleSidebar(): void {
        this.isExpanded.update(v => !v);
    }
}
