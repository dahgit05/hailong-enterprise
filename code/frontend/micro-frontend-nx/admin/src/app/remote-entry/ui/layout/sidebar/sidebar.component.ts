import { Component, signal, effect, HostListener } from '@angular/core';
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
    /** Sidebar toggle state for desktop */
    isExpanded = signal<boolean>(true);

    /** Mobile sidebar open state */
    isMobileOpen = signal<boolean>(false);

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

    /** Toggle sidebar (desktop) */
    toggleSidebar(): void {
        this.isExpanded.update(v => !v);
    }

    /** Open mobile sidebar */
    openMobileSidebar(): void {
        this.isMobileOpen.set(true);
        document.body.style.overflow = 'hidden';
    }

    /** Close mobile sidebar */
    closeMobileSidebar(): void {
        this.isMobileOpen.set(false);
        document.body.style.overflow = '';
    }

    /** Toggle mobile sidebar */
    toggleMobileSidebar(): void {
        if (this.isMobileOpen()) {
            this.closeMobileSidebar();
        } else {
            this.openMobileSidebar();
        }
    }

    /** Close mobile sidebar on route change */
    onNavClick(): void {
        if (this.isMobileOpen()) {
            this.closeMobileSidebar();
        }
    }

    /** Close on Escape key */
    @HostListener('document:keydown.escape')
    onEscapeKey(): void {
        if (this.isMobileOpen()) {
            this.closeMobileSidebar();
        }
    }
}
