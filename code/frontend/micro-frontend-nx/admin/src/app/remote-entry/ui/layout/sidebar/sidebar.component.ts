import { Component, signal, effect, HostListener } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ChangePasswordDialogComponent } from '../../../features/menu-management/components/change-password-dialog/change-password-dialog.component';

interface NavItem {
    icon: string;
    route: string;
    tooltip: string;
}

@Component({
    selector: 'app-admin-sidebar',
    standalone: true,
    imports: [CommonModule, RouterModule, ChangePasswordDialogComponent],
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

    /** User menu open state */
    isUserMenuOpen = signal<boolean>(false);

    /** Change password dialog state */
    isChangePasswordOpen = signal<boolean>(false);

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

    /** Toggle user menu */
    toggleUserMenu(event?: MouseEvent): void {
        event?.stopPropagation();
        this.isUserMenuOpen.update(v => !v);
    }

    /** Close user menu */
    closeUserMenu(): void {
        this.isUserMenuOpen.set(false);
    }

    // Actions
    logout(): void {
        console.log('Logout clicked');
        this.closeUserMenu();
    }

    changePassword(): void {
        console.log('Open Change password dialog');
        this.closeUserMenu();
        this.isChangePasswordOpen.set(true);
    }

    closeChangePassword(): void {
        this.isChangePasswordOpen.set(false);
    }

    openSettings(): void {
        console.log('Open settings clicked');
        this.closeUserMenu();
    }

    /** Close on Escape key */
    @HostListener('document:keydown.escape')
    onEscapeKey(): void {
        if (this.isMobileOpen()) {
            this.closeMobileSidebar();
        }
        if (this.isUserMenuOpen()) {
            this.closeUserMenu();
        }
    }

    /** Close user menu on click outside */
    @HostListener('document:click')
    onDocumentClick(): void {
        if (this.isUserMenuOpen()) {
            this.closeUserMenu();
        }
    }
}
