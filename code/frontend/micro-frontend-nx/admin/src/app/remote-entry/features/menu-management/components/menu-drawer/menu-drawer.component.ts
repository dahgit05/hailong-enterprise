import { Component, EventEmitter, Input, Output, signal } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
    selector: 'app-menu-drawer',
    standalone: true,
    imports: [CommonModule],
    templateUrl: './menu-drawer.component.html',
    styles: [`
        .custom-scrollbar::-webkit-scrollbar {
            width: 6px;
        }
        .custom-scrollbar::-webkit-scrollbar-track {
            background: transparent;
        }
        .custom-scrollbar::-webkit-scrollbar-thumb {
            background: rgba(32, 78, 74, 0.1);
            border-radius: 20px;
        }
        .custom-scrollbar::-webkit-scrollbar-thumb:hover {
            background: rgba(32, 78, 74, 0.2);
        }
        .toggle-switch[data-active="true"] {
            @apply bg-primary;
        }
        .toggle-switch[data-active="true"] .toggle-dot {
            @apply translate-x-[22px];
        }
    `]
})
export class MenuDrawerComponent {
    @Input() isOpen = false;
    @Output() close = new EventEmitter<void>();
    @Output() save = new EventEmitter<any>();

    activeStatus = signal(true);

    toggleActive() {
        this.activeStatus.update(v => !v);
    }

    onClose() {
        this.close.emit();
    }

    onSave() {
        this.save.emit({
            name: 'New Menu', // Placeholder logic
            active: this.activeStatus()
        });
    }
}
