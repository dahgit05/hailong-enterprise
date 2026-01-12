import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';

@Component({
    selector: 'app-user-detail-drawer',
    standalone: true,
    imports: [CommonModule, DatePipe],
    templateUrl: './user-detail-drawer.component.html',
    styleUrls: ['./user-detail-drawer.component.css']
})
export class UserDetailDrawerComponent {
    @Input() isOpen = false;
    @Input() user: any = null; // Replace 'any' with your User interface
    @Output() close = new EventEmitter<void>();
    @Output() edit = new EventEmitter<any>();

    onClose() {
        this.close.emit();
    }

    onEdit() {
        this.edit.emit(this.user);
    }
}
