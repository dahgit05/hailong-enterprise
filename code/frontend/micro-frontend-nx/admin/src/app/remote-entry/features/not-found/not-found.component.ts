import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
    selector: 'app-not-found',
    standalone: true,
    imports: [CommonModule, RouterModule],
    templateUrl: './not-found.component.html',
    styles: [`
    @import url('https://fonts.googleapis.com/css2?family=Big+Shoulders+Display:wght@900&display=swap');
    
    :host {
      display: block;
      height: 100%;
    }
    .stencil-text {
        font-family: 'Big Shoulders Display', sans-serif;
        letter-spacing: 0.05em;
        background: linear-gradient(180deg, #204E4A 0%, #0f2422 100%);
        -webkit-background-clip: text;
        -webkit-text-fill-color: transparent;
        position: relative;
    }
    :host-context(.dark) .stencil-text {
        background: linear-gradient(180deg, #34d399 0%, #10b981 100%);
        -webkit-background-clip: text;
        -webkit-text-fill-color: transparent;
    }
    .warehouse-grid {
        background-size: 40px 40px;
        background-image: radial-gradient(circle, #e2e8f0 1px, transparent 1px);
    }
    :host-context(.dark) .warehouse-grid {
        background-image: radial-gradient(circle, #1e293b 1px, transparent 1px);
    }
    .pipe-connector {
        position: absolute;
        background: #cbd5e1;
        border-radius: 99px;
    }
    :host-context(.dark) .pipe-connector {
        background: #334155;
    }
  `]
})
export class NotFoundComponent {
    // Basic theme toggle logic if independent toggle is needed, 
    // but ideally we rely on the layout or global service. 
    // For now, replicating simple toggle to match the button UI in the snippet.
    toggleTheme() {
        const html = document.documentElement;
        if (html.classList.contains('dark')) {
            html.classList.remove('dark');
            localStorage.setItem('theme', 'light');
        } else {
            html.classList.add('dark');
            localStorage.setItem('theme', 'dark');
        }
    }
}
