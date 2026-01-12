import { Component, signal, ElementRef, HostListener, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MenuDrawerComponent } from './components/menu-drawer/menu-drawer.component';
import { UserDrawerComponent } from './components/user-drawer/user-drawer.component';
import { UserDetailDrawerComponent } from './components/user-detail-drawer/user-detail-drawer.component';
import { ResetPasswordDialogComponent } from './components/reset-password-dialog/reset-password-dialog.component';
import { DeactivateUserDialogComponent } from './components/deactivate-user-dialog/deactivate-user-dialog.component';

interface AppModule {
    id: string;
    name: string;
    description: string;
    icon: string;
    active: boolean;
}

interface MenuItem {
    id: string;
    name: string;
    path: string;
    icon: string;
    order: number;
    visible: boolean;
    parentId: string | null;
}

interface User {
    id: string;
    name: string;
    email: string;
    phone: string;
    avatar: string;
    status: 'active' | 'inactive';
    online?: boolean;
}

@Component({
    selector: 'app-menu-management',
    standalone: true,
    imports: [CommonModule, MenuDrawerComponent, UserDrawerComponent, UserDetailDrawerComponent, ResetPasswordDialogComponent, DeactivateUserDialogComponent],
    templateUrl: './menu-management.component.html',
    host: {
        'class': 'flex flex-col flex-1 h-full overflow-hidden'
    }
})
export class MenuManagementComponent {
    /** App modules */
    appModules: AppModule[] = [
        { id: 'hrm', name: 'Nhân sự (HRM)', description: 'Quản lý định biên, lương & phúc lợi', icon: 'badge', active: false },
        { id: 'machine', name: 'Máy móc & Thiết bị', description: 'Tài sản cố định & Giám sát IoT', icon: 'precision_manufacturing', active: true },
        { id: 'factory', name: 'Nhà máy Thông minh', description: 'Vận hành sản xuất & Chuỗi cung ứng', icon: 'domain', active: false },
    ];

    /** Icon classes for each app when inactive */
    private appIconStyles = [
        'bg-orange-50 text-orange-500',
        'bg-primary/10 text-primary',
        'bg-blue-50 text-blue-500'
    ];

    /** Active tab */
    activeTab = signal<'menu' | 'users' | 'permissions'>('menu');

    /** Expanded state for parent menu items */
    expandedItems = signal<Set<string>>(new Set(['2', '6', '9', '14']));

    /** Selected menu item ID */
    selectedItem = signal<string | null>(null);

    /** Menu items */
    menuItems: MenuItem[] = [
        { id: '1', name: 'Bảng điều khiển', path: '/dashboard', icon: 'space_dashboard', order: 1, visible: true, parentId: null },
        { id: '2', name: 'Bảo trì & Sửa chữa', path: '/maintenance', icon: 'settings_accessibility', order: 2, visible: true, parentId: null },
        { id: '3', name: 'Danh sách thiết bị', path: '/maintenance/assets', icon: 'list_alt', order: 1, visible: true, parentId: '2' },
        { id: '4', name: 'Lịch bảo trì định kỳ', path: '/maintenance/schedule', icon: 'calendar_month', order: 2, visible: true, parentId: '2' },
        { id: '5', name: 'Lịch sử sửa chữa', path: '/maintenance/history', icon: 'history_edu', order: 3, visible: true, parentId: '2' },
        { id: '6', name: 'Báo cáo & Phân tích', path: '/analytics', icon: 'analytics', order: 3, visible: true, parentId: null },
        { id: '7', name: 'Báo cáo tổng hợp', path: '/analytics/summary', icon: 'summarize', order: 1, visible: true, parentId: '6' },
        { id: '8', name: 'Biểu đồ thống kê', path: '/analytics/charts', icon: 'bar_chart', order: 2, visible: true, parentId: '6' },
        { id: '9', name: 'Quản lý Kho vận', path: '/warehouse', icon: 'inventory_2', order: 4, visible: true, parentId: null },
        { id: '10', name: 'Nhập kho', path: '/warehouse/import', icon: 'move_to_inbox', order: 1, visible: true, parentId: '9' },
        { id: '11', name: 'Xuất kho', path: '/warehouse/export', icon: 'outbox', order: 2, visible: true, parentId: '9' },
        { id: '12', name: 'Tồn kho', path: '/warehouse/inventory', icon: 'inventory', order: 3, visible: true, parentId: '9' },
        { id: '13', name: 'Giám sát Chất lượng', path: '/quality-control', icon: 'monitoring', order: 5, visible: false, parentId: null },
        { id: '14', name: 'Quản lý Chuỗi cung ứng', path: '/supply-chain', icon: 'hub', order: 6, visible: true, parentId: null },
        { id: '15', name: 'Nhà cung cấp', path: '/supply-chain/suppliers', icon: 'storefront', order: 1, visible: true, parentId: '14' },
        { id: '16', name: 'Đơn đặt hàng', path: '/supply-chain/orders', icon: 'shopping_cart', order: 2, visible: true, parentId: '14' },
        { id: '17', name: 'Tài chính & Kế toán', path: '/finance', icon: 'payments', order: 7, visible: true, parentId: null },
        { id: '18', name: 'Quản lý Nhân sự', path: '/hrm', icon: 'badge', order: 8, visible: true, parentId: null },
        { id: '19', name: 'Cài đặt Hệ thống', path: '/settings', icon: 'settings', order: 9, visible: true, parentId: null },
    ];

    /** Users data */
    users: User[] = [
        {
            id: '00124',
            name: 'Trần Đức Quân',
            email: 'tranducquan@industrial.vn',
            phone: '0912 112 112',
            avatar: 'https://lh3.googleusercontent.com/aida-public/AB6AXuCW-91bXx4KXGdpNN2wJniw-BKf34MJYYqZxQEy9CSzg2uJ8QAkAgKPFhiI47vXRx7OQBkNKuMu6VG8cPtSGBS04J-c-1AtmpO3V47e2tFNX5rCBv_kAhY4bFNm96l6-9cx62LjvH3UWgpxJGL8AR4cClcFUxhEDMvimi44yHIgVDQmzTDkBpx_e0ajiKVMf842LDvY5fIKqPEi9ZV9UNuO5IKaNR2fKAH6F7fmlcmPftgvufIpZxKTfGkJNytlJ0XGoVYqXu_BzJg',
            status: 'active',
            online: true
        },
        {
            id: '00125',
            name: 'Phạm Đình Minh',
            email: 'phamdinhminh@industrial.vn',
            phone: '0912 446 546',
            avatar: 'https://lh3.googleusercontent.com/aida-public/AB6AXuCfPnHT-XpMuSKf1DtWfnMoBatFJJ_tAa3cqGNryBZDzLrxui49I9FAoZ-R0nRTWFuCscZv6FodvihZRDY7Z8p8f-ZTpoNr1_Xom9d2EXYO6tpKI-FKttULqRimtIwYGCrhvXDD4hDcwIIZYyss1hO2L9vy4zgFRGRRpi-vvvdkcm7qtidvA3RNFZ9VtAoS7eSodpjh3clKURs2RUERjOVpUlKblPCEC8xx4x1P8ZbsMnPO8pe0_LxuLKpuMxOdEw2WE-trH7cwi10',
            status: 'active',
            online: false
        },
        {
            id: '00126',
            name: 'Nguyễn Duy Chung',
            email: 'nguyenduychung@industrial.vn',
            phone: '0988 888 888',
            avatar: 'https://lh3.googleusercontent.com/aida-public/AB6AXuDhtDBz8ujLrGNowdeRoopYXoYSdSETRNqWrMa062YEyln9BD-HJ3ZAZeRo4jHXwmU8uJtOSkSnH1aTrFdJr2Vo5WjE1DXSkhdWWy8vQdGdcl4tA53P7off9s7LrRSuLUjXsJmX1T4STk3EcCJrwIrktxOJ1nELQGplOPWlX60TxWUqLGVxfMej8wXoXGOF-wW84QXPm6Qm_VYIKrQWJ-fXLV49MA4kAxOHPyfCM3P61GFVbNXCDZD64l4jUsIa3SoUbU-6Q3bNxJ4',
            status: 'inactive',
            online: false
        }
    ];

    /** Context Menu State */
    contextMenu = signal<{ visible: boolean; x: number; y: number; item: MenuItem | null }>({
        visible: false,
        x: 0,
        y: 0,
        item: null
    });

    @ViewChild('contextMenuRef') contextMenuRef!: ElementRef;

    /** Get parent menu items */
    get parentMenuItems(): MenuItem[] {
        return this.menuItems.filter(item => !item.parentId).sort((a, b) => a.order - b.order);
    }

    /** Get child menu items */
    getChildItems(parentId: string): MenuItem[] {
        return this.menuItems.filter(item => item.parentId === parentId).sort((a, b) => a.order - b.order);
    }

    /** Has children */
    hasChildren(itemId: string): boolean {
        return this.menuItems.some(item => item.parentId === itemId);
    }

    /** Check if item is expanded */
    isExpanded(itemId: string): boolean {
        return this.expandedItems().has(itemId);
    }

    /** Toggle expand/collapse */
    toggleExpand(itemId: string, event: Event): void {
        event.stopPropagation();
        const expanded = new Set(this.expandedItems());
        if (expanded.has(itemId)) {
            expanded.delete(itemId);
        } else {
            expanded.add(itemId);
        }
        this.expandedItems.set(expanded);
    }

    /** Check if item is selected */
    isSelected(itemId: string): boolean {
        return this.selectedItem() === itemId;
    }

    /** Select item */
    selectMenuItem(itemId: string): void {
        this.selectedItem.set(itemId);
    }

    /** Get app icon classes based on index */
    getAppIconClasses(index: number): string {
        return this.appIconStyles[index] || 'bg-slate-50 text-slate-500';
    }

    /** Select app */
    selectApp(appId: string): void {
        this.appModules.forEach(app => app.active = app.id === appId);
    }

    /** Set active tab */
    setActiveTab(tab: 'menu' | 'users' | 'permissions'): void {
        this.activeTab.set(tab);
    }

    /** Format order number */
    formatOrder(order: number): string {
        return order.toString().padStart(2, '0');
    }

    /** Handle right click on menu item */
    onContextMenu(event: MouseEvent, item: MenuItem): void {
        event.preventDefault();
        event.stopPropagation();
        this.selectedItem.set(item.id);
        this.contextMenu.set({
            visible: true,
            x: event.clientX,
            y: event.clientY,
            item: item
        });
    }

    /** Close context menu on click outside */
    @HostListener('document:click', ['$event'])
    onDocumentClick(event: MouseEvent): void {
        if (this.contextMenu().visible) {
            this.contextMenu.set({
                visible: false,
                x: 0,
                y: 0,
                item: null
            });
        }
        if (this.userContextMenu().visible) {
            this.closeUserContextMenu();
        }
    }

    /** Copy path to clipboard */
    copyPath(): void {
        const item = this.contextMenu().item;
        if (item) {
            navigator.clipboard.writeText(item.path);
        }
    }

    /** User Drawer State */
    isUserDrawerOpen = signal(false);
    selectedUserForEdit = signal<User | null>(null);

    /** User Drawer */
    openUserDrawer(user: User | null = null): void {
        this.selectedUserForEdit.set(user);
        this.isUserDrawerOpen.set(true);
    }

    closeUserDrawer(): void {
        this.isUserDrawerOpen.set(false);
        this.selectedUserForEdit.set(null);
    }

    onSaveUser(userData: any): void {
        console.log('Save user:', userData);
        this.closeUserDrawer();
    }

    /** User Detail Drawer State */
    isUserDetailDrawerOpen = signal(false);
    selectedUserForDetail = signal<User | null>(null);

    /** User Detail Actions */
    openUserDetailDrawer(user: User): void {
        this.selectedUserForDetail.set(user);
        this.isUserDetailDrawerOpen.set(true);
    }

    closeUserDetailDrawer(): void {
        this.isUserDetailDrawerOpen.set(false);
        this.selectedUserForDetail.set(null);
    }

    onEditUserFromDetail(user: User): void {
        this.closeUserDetailDrawer();
        this.openUserDrawer(user);
    }

    /** Dialog States */
    isResetPasswordOpen = signal(false);
    isDeactivateUserOpen = signal(false);
    selectedUserForAction = signal<User | null>(null);

    closeResetPasswordDialog(): void {
        this.isResetPasswordOpen.set(false);
        this.selectedUserForAction.set(null);
    }

    closeDeactivateUserDialog(): void {
        this.isDeactivateUserOpen.set(false);
        this.selectedUserForAction.set(null);
    }

    onResetPasswordConfirm(password: string): void {
        console.log('Reset password for user:', this.selectedUserForAction()?.name, 'New password:', password);
        // Implement API call here
        this.closeResetPasswordDialog();
    }

    onDeactivateUserConfirm(): void {
        const user = this.selectedUserForAction();
        if (user) {
            console.log('Deactivate user:', user.name);
            // Update local state for demo
            const userIndex = this.users.findIndex(u => u.id === user.id);
            if (userIndex !== -1) {
                this.users[userIndex].status = 'inactive';
            }
        }
        this.closeDeactivateUserDialog();
    }

    /** Drawer State */
    isDrawerOpen = signal(false);

    openDrawer(): void {
        this.isDrawerOpen.set(true);
    }

    closeDrawer(): void {
        this.isDrawerOpen.set(false);
    }

    onSaveMenu(data: any): void {
        console.log('Saving menu:', data);
        // Implement save logic here
        this.closeDrawer();
    }

    /** Context menu actions */
    editMenu(): void {
        console.log('Edit menu:', this.contextMenu().item);
        this.openDrawer(); // For demo, reuse drawer for edit
    }

    addSubMenu(): void {
        console.log('Add submenu for:', this.contextMenu().item);
        this.openDrawer(); // Open drawer for add submenu
    }

    deleteMenu(): void {
        if (this.contextMenu().item && confirm(`Xóa menu "${this.contextMenu().item?.name}"?`)) {
            const item = this.contextMenu().item;
            if (item) {
                this.menuItems = this.menuItems.filter(m => m.id !== item.id && m.parentId !== item.id);
            }
        }
    }

    /** User Context Menu State */
    userContextMenu = signal<{ visible: boolean; x: number; y: number; user: User | null }>({
        visible: false,
        x: 0,
        y: 0,
        user: null
    });

    /** Handle right click on user card */
    onUserContextMenu(event: MouseEvent, user: User): void {
        console.log('onUserContextMenu triggered', user);
        event.preventDefault();
        event.stopPropagation();
        this.userContextMenu.set({
            visible: true,
            x: event.clientX,
            y: event.clientY,
            user: user
        });
        console.log('Context menu state updated', this.userContextMenu());
    }

    /** User context menu actions */
    editUser(): void {
        const user = this.userContextMenu().user;
        if (user) {
            this.openUserDrawer(user);
        }
        this.closeUserContextMenu();
        // Implement edit user logic
    }

    viewUserDetails(): void {
        const user = this.userContextMenu().user;
        if (user) {
            this.openUserDetailDrawer(user);
        }
        this.closeUserContextMenu();
    }

    resetUserPassword(): void {
        const user = this.userContextMenu().user;
        if (user) {
            this.selectedUserForAction.set(user);
            this.isResetPasswordOpen.set(true);
        }
        this.closeUserContextMenu();
    }

    manageUserPermissions(): void {
        console.log('Manage permissions for:', this.userContextMenu().user);
        this.closeUserContextMenu();
        // Implement permissions management logic
    }

    deactivateUser(): void {
        const user = this.userContextMenu().user;
        if (user) {
            this.selectedUserForAction.set(user);
            this.isDeactivateUserOpen.set(true);
        }
        this.closeUserContextMenu();
    }

    closeUserContextMenu(): void {
        this.userContextMenu.set({
            visible: false,
            x: 0,
            y: 0,
            user: null
        });
    }
}
