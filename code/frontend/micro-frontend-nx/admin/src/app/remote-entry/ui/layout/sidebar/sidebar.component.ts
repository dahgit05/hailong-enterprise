import { Component, signal, computed, effect, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

interface NavItem {
    icon: string;
    label: string;
    route: string;
    tooltip: string;
}

@Component({
    selector: 'app-admin-sidebar',
    standalone: true,
    imports: [CommonModule, RouterModule],
    templateUrl: './sidebar.component.html',
})
export class AdminSidebarComponent implements OnInit {
    private readonly STORAGE_KEY = 'admin-sidebar-expanded';

    /** Signal for sidebar expanded state */
    isExpanded = signal<boolean>(true);

    /** Computed class for sidebar width */
    sidebarWidth = computed(() => this.isExpanded() ? 'w-64' : 'w-20');

    /** Navigation items */
    navItems: NavItem[] = [
        { icon: 'apps', label: 'Quản lý Ứng dụng', route: '/admin', tooltip: 'Quản lý Ứng dụng' },
        { icon: 'history', label: 'Nhật ký hệ thống', route: '/admin/logs', tooltip: 'Nhật ký hệ thống' },
        { icon: 'shield_person', label: 'Bảo mật', route: '/admin/security', tooltip: 'Bảo mật' },
    ];

    constructor() {
        // Persist state to localStorage
        effect(() => {
            localStorage.setItem(this.STORAGE_KEY, JSON.stringify(this.isExpanded()));
        });
    }

    ngOnInit(): void {
        // Restore state from localStorage
        const stored = localStorage.getItem(this.STORAGE_KEY);
        if (stored !== null) {
            this.isExpanded.set(JSON.parse(stored));
        }
    }

    /** Toggle sidebar expanded/collapsed state */
    toggleSidebar(): void {
        this.isExpanded.update(v => !v);
    }
}
