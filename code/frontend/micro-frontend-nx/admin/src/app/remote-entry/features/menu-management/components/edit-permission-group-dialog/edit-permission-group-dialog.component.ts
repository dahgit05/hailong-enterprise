import { Component, EventEmitter, Input, Output, OnChanges, SimpleChanges } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
    selector: 'app-edit-permission-group-dialog',
    standalone: true,
    imports: [CommonModule, FormsModule],
    templateUrl: './edit-permission-group-dialog.component.html',
    styleUrls: ['./edit-permission-group-dialog.component.css']
})
export class EditPermissionGroupDialogComponent implements OnChanges {
    @Input() isOpen = false;
    @Input() mode: 'create' | 'edit' = 'create';
    @Input() group: any = null;
    @Output() close = new EventEmitter<void>();
    @Output() save = new EventEmitter<any>();

    formData = {
        code: '',
        name: ''
    };

    ngOnChanges(changes: SimpleChanges) {
        if (changes['isOpen'] && this.isOpen) {
            if (this.mode === 'edit' && this.group) {
                this.formData = {
                    code: this.group.code || 'ADMIN_SYS_01',
                    name: this.group.name || ''
                };
            } else {
                this.formData = {
                    code: '',
                    name: ''
                };
            }
        }
    }

    onClose() {
        this.close.emit();
    }

    onSave() {
        this.save.emit({ mode: this.mode, data: this.formData });
    }
}
