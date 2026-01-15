import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
    selector: 'app-permission-sidebar',
    standalone: true,
    imports: [CommonModule],
    templateUrl: './permission-sidebar.component.html',
    styleUrls: ['./permission-sidebar.component.css']
})
export class PermissionSidebarComponent {
    @Input() isOpen = false;
    @Output() close = new EventEmitter<void>();

    // Mock data for permission library
    permissions = [
        { name: 'Xem báo cáo vận hành', description: 'Cho phép truy cập các báo cáo tổng hợp.', icon: 'visibility', colorClass: 'text-primary dark:text-emerald-400' },
        { name: 'Sửa định mức vật tư', description: 'Cập nhật thông số nguyên vật liệu.', icon: 'edit_square', colorClass: 'text-primary dark:text-emerald-400' },
        { name: 'Nhập dữ liệu IoT', description: 'Gửi dữ liệu thủ công lên hệ thống.', icon: 'upload_file', colorClass: 'text-primary dark:text-emerald-400' },
        { name: 'Xóa nhật ký hệ thống', description: 'Quyền cực cao (Super Admin).', icon: 'delete', colorClass: 'text-slate-400', opacity: true },
        { name: 'Quản lý User nhân sự', description: 'Thay đổi hồ sơ HRM.', icon: 'manage_accounts', colorClass: 'text-primary dark:text-emerald-400' },
    ];

    onClose() {
        this.close.emit();
    }
}
