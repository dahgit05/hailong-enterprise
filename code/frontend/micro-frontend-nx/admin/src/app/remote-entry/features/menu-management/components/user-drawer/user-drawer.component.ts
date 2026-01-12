import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
    selector: 'app-user-drawer',
    standalone: true,
    imports: [CommonModule],
    templateUrl: './user-drawer.component.html',
    styleUrls: ['./user-drawer.component.css']
})
export class UserDrawerComponent {
    @Input() isOpen = false;
    @Input() user: any = null; // Replace 'any' with your User interface
    @Output() close = new EventEmitter<void>();
    @Output() save = new EventEmitter<any>();

    onClose() {
        this.close.emit();
    }

    onSave() {
        // Collect form data logic here
        this.save.emit(this.user);
    }
}
