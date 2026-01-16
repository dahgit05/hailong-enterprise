import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
    selector: 'app-coming-soon',
    standalone: true,
    imports: [CommonModule, RouterModule],
    templateUrl: './coming-soon.component.html',
    styles: [`
    :host {
      display: block;
    }
    .industrial-grid {
        background-image: radial-gradient(#cbd5e1 0.5px, transparent 0.5px);
        background-size: 24px 24px;
    }
    :host-context(.dark) .industrial-grid {
        background-image: radial-gradient(#334155 0.5px, transparent 0.5px);
    }
    .floating-element {
        animation: floating 6s ease-in-out infinite;
    }
    @keyframes floating {
        0%, 100% { transform: translateY(0) rotate(0deg); }
        50% { transform: translateY(-20px) rotate(2deg); }
    }
  `]
})
export class ComingSoonComponent { }
