import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
    selector: 'app-deactivate-user-dialog',
    standalone: true,
    imports: [CommonModule],
    templateUrl: './deactivate-user-dialog.component.html',
    styleUrls: ['./deactivate-user-dialog.component.css']
})
export class DeactivateUserDialogComponent {
    @Input() isOpen = false;
    @Input() user: any = null;
    @Output() close = new EventEmitter<void>();
    @Output() confirm = new EventEmitter<void>();

    onClose() {
        this.close.emit();
    }

    onConfirm() {
        this.confirm.emit();
        this.onClose();
    }
}
