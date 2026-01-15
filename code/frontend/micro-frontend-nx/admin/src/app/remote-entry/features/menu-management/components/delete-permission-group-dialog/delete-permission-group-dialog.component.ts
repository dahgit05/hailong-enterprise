import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
    selector: 'app-delete-permission-group-dialog',
    standalone: true,
    imports: [CommonModule],
    templateUrl: './delete-permission-group-dialog.component.html',
    styleUrls: ['./delete-permission-group-dialog.component.css']
})
export class DeletePermissionGroupDialogComponent {
    @Input() isOpen = false;
    @Input() group: any = null;
    @Output() close = new EventEmitter<void>();
    @Output() confirm = new EventEmitter<void>();

    onClose() {
        this.close.emit();
    }

    onConfirm() {
        this.confirm.emit();
    }
}
