import { Component, signal, ElementRef, HostListener, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';

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

@Component({
    selector: 'app-menu-management',
    standalone: true,
    imports: [CommonModule],
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
        return this.menuItems.filter(item => !item.parentId);
    }

    /** Get child menu items */
    getChildItems(parentId: string): MenuItem[] {
        return this.menuItems.filter(item => item.parentId === parentId);
    }

    /** Has children */
    hasChildren(itemId: string): boolean {
        return this.menuItems.some(item => item.parentId === itemId);
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
        this.contextMenu.set({
            visible: true,
            x: event.pageX,
            y: event.pageY,
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
    }

    /** Context menu actions */
    editMenu(): void {
        console.log('Edit menu:', this.contextMenu().item);
    }

    addSubMenu(): void {
        console.log('Add submenu for:', this.contextMenu().item);
    }

    deleteMenu(): void {
        if (this.contextMenu().item && confirm(`Xóa menu "${this.contextMenu().item?.name}"?`)) {
            const item = this.contextMenu().item;
            if (item) {
                this.menuItems = this.menuItems.filter(m => m.id !== item.id && m.parentId !== item.id);
            }
        }
    }
}
