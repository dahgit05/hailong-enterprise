import { Component, EventEmitter, Input, Output, signal } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
    selector: 'app-permission-matrix-drawer',
    standalone: true,
    imports: [CommonModule],
    templateUrl: './permission-matrix-drawer.component.html',
    styleUrls: ['./permission-matrix-drawer.component.css']
})
export class PermissionMatrixDrawerComponent {
    @Input() isOpen = false;
    @Input() data: any = null;
    @Output() close = new EventEmitter<void>();
    @Output() save = new EventEmitter<any>();

    users = signal([
        { id: '1', name: 'Nguyễn Xuân Hoàng', email: 'hoang.nx@industrial.vn', avatar: 'https://lh3.googleusercontent.com/aida-public/AB6AXuBGqHjvun6ooh9EZp31aKfNYL1Cyx0ZA9TGvYgOLvLmpVKv3HvsuSsEP1bxI9Zr1FqjqO5SizbjR58QUOLOqOtKWmutr6OyoZdqsH_hcJ6BfexZkA9YbtMO4SgnNW_Vh7rK1qWceQk9pxsqGJyfQWzOAXv20bdQzkrdKhwwSafmqaji_RcUuEWk70ykaOiH0i1fGAwuqh84eyyWPrRCArqMb92ZdaYKhk_pU-GKLPx-vA4komOb2pQv2qBnCorjl0M-lPky9cC5Yrw' },
        { id: '2', name: 'Lê Minh Anh', email: 'anh.lm@industrial.vn', avatar: 'https://lh3.googleusercontent.com/aida-public/AB6AXuB8sryv1bXYYAYUxsgYp863K4CzsvNFcEyuwI3KzjqUGPRadmtSzGKo8sqV96ibbcK6XfBz8tYgmj35qil5sC_yItXMGimcQSNNEjmLeqTz6JlzDWssC9IM-3JsIafn9zt_LgNwHTfX7K73h845M0PQlJNfWGogQhG67FR8sskqKZFiXtLfXQ3QOwHVmf2eKiFy13CiVPfD4JFkttJbZCGvaMxw5BiAae0B48gALHm5uKVhtX4hJWURXi1cnh_hfInDFDBJU5YSIsg' },
        { id: '3', name: 'Trần Quốc Bảo', email: 'bao.tq@industrial.vn', avatar: 'https://lh3.googleusercontent.com/aida-public/AB6AXuD-1o4vgqZqjujWBzjmDEaHI677F8UsN2WORLpakkX9YmD7bLKas8ORAJZzPz37o56lq44sZHAFSDOTFBbilTo-EJItWiVWgbvHjsUO04zKQlsB3QmtS0KpRVWrFYPlShZuQuSKnZIBXKoBqKOB9bdYBRcPZ_UziUwbDqka5BDWvuY-5c44dl5u0Zc7MPqMI-yQPAMWiVZOOoCjHjOiEJG4jMDNQCaBQImRV57ldOWeS_mxuCRsvyNimA1T1A4u1hGThdMzSX7frQg' },
        { id: '4', name: 'Đặng Thùy Trang', email: 'trang.dt@industrial.vn', initials: 'DT' }
    ]);

    permissions = signal([
        { id: 'dashboard', name: 'Quản lý Dashboard', desc: 'Giao diện vận hành tổng quan', view: true, create: false, edit: false, delete: false },
        { id: 'inventory', name: 'Quản lý Kho bãi', desc: 'Xuất nhập tồn và kiểm kê', view: true, create: true, edit: false, delete: false },
        { id: 'production', name: 'Báo cáo Sản lượng', desc: 'Truy xuất OEE & KPI sản xuất', view: true, create: false, edit: false, delete: false },
        { id: 'device', name: 'Giám sát Thiết bị', desc: 'Trạng thái máy móc thời gian thực', view: true, create: true, edit: true, delete: false },
        { id: 'factory', name: 'Cấu hình Nhà máy', desc: 'Thiết lập tham số xưởng', view: true, create: false, edit: true, delete: false },
        { id: 'order', name: 'Quản lý Lệnh sản xuất', desc: 'Lập kế hoạch và điều phối', view: true, create: true, edit: true, delete: false },
    ]);

    onClose() {
        this.close.emit();
    }

    onSave() {
        this.save.emit(this.permissions());
    }

    removeUser(userId: string) {
        this.users.update(users => users.filter(u => u.id !== userId));
    }

    toggleAll(field: 'view' | 'create' | 'edit' | 'delete', event: Event) {
        const isChecked = (event.target as HTMLInputElement).checked;
        this.permissions.update(perms => perms.map(p => ({ ...p, [field]: isChecked })));
    }

    isAllChecked(field: 'view' | 'create' | 'edit' | 'delete'): boolean {
        return this.permissions().length > 0 && this.permissions().every((p: any) => p[field]);
    }
}
