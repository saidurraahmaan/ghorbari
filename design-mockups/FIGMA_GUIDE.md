# Complete Figma Design Guide for Ghorbari

This guide will help you recreate the Ghorbari apartment management system designs in Figma.

## 📋 Table of Contents

1. [Initial Setup](#initial-setup)
2. [Design System](#design-system)
3. [Component Library](#component-library)
4. [Page Layouts](#page-layouts)
5. [Prototyping](#prototyping)
6. [Export & Handoff](#export--handoff)

---

## 1. Initial Setup

### Step 1: Create New Figma File

1. Go to [Figma.com](https://figma.com)
2. Click "New Design File"
3. Rename to "Ghorbari - Apartment Management"

### Step 2: Install Inter Font

1. Download Inter from [Google Fonts](https://fonts.google.com/specimen/Inter)
2. Install on your system
3. Or use Figma's font menu to select Inter

### Step 3: Create Frame Structure

Create a page structure:
```
📄 Pages (Left sidebar)
├── 🎨 Design System
├── 🧩 Components
├── 📱 Wireframes
├── 🎨 Mockups (High Fidelity)
└── 🔗 Prototype
```

To create pages:
- Right-click in the canvas
- Select "Add page" or use shortcut: `Ctrl/Cmd + /` → "Add page"

### Step 4: Set Up Frames

On the Wireframes page, create frames:

1. Press `F` (Frame tool)
2. Select from right sidebar:
   - Desktop: 1440 × 1024
   - Tablet: 768 × 1024
   - Mobile: 375 × 812

---

## 2. Design System

### A. Color Styles

Navigate to "Design System" page.

**Create Color Palette:**

1. Create rectangles for each color
2. Select rectangle → Click fill color
3. Right sidebar → Click "+" next to "Styles"
4. Name the style: `primary/500`

**Color Values:**

```
PRIMARY
├── primary/50:  #EFF6FF
├── primary/100: #DBEAFE
├── primary/500: #3B82F6 ⭐ Main
├── primary/600: #2563EB
└── primary/700: #1D4ED8

SUCCESS
├── success/50:  #ECFDF5
├── success/500: #10B981 ⭐ Main
└── success/600: #059669

WARNING
├── warning/50:  #FFFBEB
├── warning/500: #F59E0B ⭐ Main
└── warning/600: #D97706

DANGER
├── danger/50:   #FEF2F2
├── danger/500:  #EF4444 ⭐ Main
└── danger/600:  #DC2626

GRAY
├── gray/50:     #F9FAFB (Background)
├── gray/100:    #F3F4F6
├── gray/200:    #E5E7EB (Borders)
├── gray/300:    #D1D5DB
├── gray/400:    #9CA3AF
├── gray/500:    #6B7280 (Text secondary)
├── gray/600:    #4B5563
├── gray/700:    #374151
├── gray/800:    #1F2937
├── gray/900:    #111827 (Text primary)
├── white:       #FFFFFF
└── black:       #000000
```

**Quick Tip:** Create a color palette grid in Figma for easy reference.

### B. Typography Styles

Create text samples:

1. Press `T` (Text tool)
2. Type sample text
3. Set font properties in right sidebar
4. Click "+" next to "Text styles"
5. Name the style: `H1/Display Large`

**Typography System:**

```
HEADINGS

H1 / Display Large
- Family: Inter
- Weight: SemiBold (600)
- Size: 32px
- Line Height: 40px (125%)
- Letter Spacing: -0.64px (-2%)

H2 / Display Medium
- Family: Inter
- Weight: SemiBold (600)
- Size: 24px
- Line Height: 32px (133%)
- Letter Spacing: -0.24px (-1%)

H3 / Display Small
- Family: Inter
- Weight: SemiBold (600)
- Size: 20px
- Line Height: 28px (140%)
- Letter Spacing: 0

H4 / Title
- Family: Inter
- Weight: Medium (500)
- Size: 18px
- Line Height: 24px (133%)

BODY TEXT

Body / Regular
- Family: Inter
- Weight: Regular (400)
- Size: 14px
- Line Height: 20px (143%)

Body / Medium
- Family: Inter
- Weight: Medium (500)
- Size: 14px
- Line Height: 20px (143%)

Body / Semibold
- Family: Inter
- Weight: SemiBold (600)
- Size: 14px
- Line Height: 20px (143%)

Caption / Regular
- Family: Inter
- Weight: Regular (400)
- Size: 12px
- Line Height: 16px (133%)

Caption / Medium
- Family: Inter
- Weight: Medium (500)
- Size: 12px
- Line Height: 16px (133%)

Small
- Family: Inter
- Weight: Regular (400)
- Size: 11px
- Line Height: 14px (127%)
```

### C. Effect Styles (Shadows)

Create shadow styles:

1. Create a rectangle
2. Right sidebar → Effects → `+`
3. Select "Drop shadow"
4. Set values
5. Click "+" → Name style: `shadow/sm`

**Shadow Values:**

```
shadow/sm
- X: 0, Y: 1
- Blur: 2
- Color: #000000 at 5% opacity

shadow/md
- X: 0, Y: 4
- Blur: 6
- Spread: -1
- Color: #000000 at 10% opacity

shadow/lg
- X: 0, Y: 10
- Blur: 15
- Spread: -3
- Color: #000000 at 10% opacity

shadow/xl
- X: 0, Y: 20
- Blur: 25
- Spread: -5
- Color: #000000 at 10% opacity
```

### D. Grid System

Set up 8px grid:

1. Select frame
2. Right sidebar → Layout grid → `+`
3. Choose "Grid"
4. Set size: 8px
5. Color: #000000 at 5% opacity

---

## 3. Component Library

Navigate to "Components" page.

### A. Button Component

**Create Primary Button:**

1. Press `R` (Rectangle)
2. Size: 120px × 40px
3. Fill: `primary/500`
4. Corner radius: 6px
5. Add text: "Button"
6. Center text (Auto layout will do this)

**Convert to Auto Layout:**

1. Select rectangle + text
2. `Shift + A` (Auto layout)
3. Set padding: 12px horizontal, 8px vertical
4. Set spacing: 8px (if adding icon)

**Create Component:**

1. Select the auto layout frame
2. `Ctrl/Cmd + Alt + K` (Create component)
3. Rename: "Button/Primary"

**Add Variants:**

1. Right-click component → "Add variant"
2. Create variants:
   - State: Default, Hover, Disabled
   - Size: Small, Medium, Large

**Variant Properties:**

```
State = Default
├── Size = Small:  h: 32px, padding: 10px 12px, text: 12px
├── Size = Medium: h: 40px, padding: 12px 16px, text: 14px
└── Size = Large:  h: 48px, padding: 16px 20px, text: 16px

State = Hover
├── Fill: primary/600
└── (same sizes as Default)

State = Disabled
├── Fill: gray/300
├── Opacity: 50%
└── (same sizes as Default)
```

**Other Button Types:**

Create similar components for:
- Button/Secondary (white bg, primary border)
- Button/Danger (danger/500 bg)
- Button/Ghost (transparent bg)

### B. Input Component

1. Rectangle: 320px × 40px
2. Fill: white
3. Border: 1px, gray/300
4. Corner radius: 6px
5. Add text: "Enter text..."
6. Padding: 12px 16px
7. Create component: "Input/Text"

**Variants:**

```
State = Default
├── Border: gray/300
└── Text: gray/400

State = Focused
├── Border: primary/500
└── Ring: 2px primary/500 at 20% opacity

State = Error
├── Border: danger/500
└── Ring: 2px danger/500 at 20% opacity

State = Disabled
├── Fill: gray/100
└── Opacity: 50%
```

### C. Card Component

1. Rectangle: 360px × auto
2. Fill: white
3. Border: 1px, gray/200
4. Corner radius: 8px
5. Shadow: shadow/sm
6. Padding: 24px
7. Create component: "Card/Default"

**Variants:**

```
State = Default
└── Shadow: shadow/sm

State = Hover
├── Shadow: shadow/md
└── Transform: Scale 1.01
```

### D. Badge Component

1. Rectangle: auto × 24px
2. Fill: Based on type
3. Corner radius: 12px (pill)
4. Text: 12px Medium
5. Padding: 4px 12px
6. Create component: "Badge"

**Variants:**

```
Type = Success
├── Fill: success/50
└── Text: success/700

Type = Warning
├── Fill: warning/50
└── Text: warning/700

Type = Danger
├── Fill: danger/50
└── Text: danger/700

Type = Info
├── Fill: primary/50
└── Text: primary/700

Type = Neutral
├── Fill: gray/100
└── Text: gray/700
```

### E. Navigation Item Component

1. Auto layout frame: 224px × 40px
2. Fill: transparent
3. Corner radius: 6px
4. Icon placeholder: 20px × 20px
5. Text: 14px Medium
6. Padding: 12px 16px
7. Gap: 12px
8. Create component: "Nav/Item"

**Variants:**

```
State = Default
├── Fill: transparent
└── Text: gray/700

State = Active
├── Fill: primary/500
└── Text: white

State = Hover
├── Fill: gray/100
└── Text: gray/900
```

---

## 4. Page Layouts

### A. Login Page

**Frame Setup:**

1. Create frame: 1440 × 1024
2. Fill: gray/50
3. Name: "Login"

**Layout Structure:**

```
Frame (1440 × 1024)
└── Auto Layout (Vertical, centered)
    ├── Logo Container
    │   ├── Icon (80 × 80)
    │   ├── Heading: "Sunrise Towers"
    │   └── Subheading: "Property Management Portal"
    │
    └── Card (400px width)
        ├── Email Input
        ├── Password Input
        ├── Checkbox: "Remember me"
        ├── Button: "Sign In"
        └── Link: "Forgot password?"
```

**Step-by-Step:**

1. **Create centered container:**
   - Press `F` → Create frame 1440 × 1024
   - Fill: gray/50
   - Layout: Vertical center & horizontal center

2. **Add logo section:**
   - Circle: 80 × 80, fill: primary/500
   - Add icon (use Iconify plugin or draw)
   - Text: H1 style, "Sunrise Towers"
   - Text: Body/Regular, gray/500, "Property Management Portal"
   - Group → Auto layout (vertical, gap: 16px)

3. **Create card:**
   - Use Card component
   - Width: 400px
   - Add inputs (use Input component)
   - Add checkbox
   - Add button (use Button component)
   - Auto layout (vertical, gap: 16px)

4. **Add footer:**
   - Text: Caption/Regular, gray/500
   - "Secure login with 256-bit encryption"

### B. Admin Dashboard

**Frame Setup:**

1. Create frame: 1440 × 1024
2. Name: "Dashboard - Admin"

**Layout Structure:**

```
Frame (1440 × 1024)
├── Sidebar (256px width)
│   ├── Logo
│   └── Navigation Items
│
└── Main Content (1184px width)
    ├── Header (64px height)
    │   ├── Page Title
    │   ├── Notifications
    │   └── User Menu
    │
    └── Content Area
        ├── Stats Cards (4 columns)
        ├── Charts & Activities (2 columns)
        └── Tables (2 columns)
```

**Step-by-Step:**

1. **Create sidebar:**
   - Rectangle: 256 × 1024
   - Fill: white
   - Border right: 1px, gray/200
   - Pin: Left

2. **Add logo:**
   - Auto layout: horizontal, gap: 12px
   - Icon + Text
   - Padding: 16px 24px
   - Height: 64px
   - Border bottom: 1px, gray/200

3. **Add navigation:**
   - Use Nav/Item component
   - Duplicate 9 times
   - Update icons and labels
   - Auto layout (vertical, gap: 4px)
   - Padding: 16px

4. **Create header:**
   - Rectangle: 1184 × 64
   - Fill: white
   - Border bottom: 1px, gray/200
   - Auto layout: horizontal, space between
   - Left: Page title (H1)
   - Right: Notification icon + User avatar

5. **Add stats cards:**
   - Use Card component
   - Create 4 cards
   - Auto layout: horizontal, gap: 24px
   - Each card:
     - Icon (emoji or icon)
     - Label (Caption)
     - Value (H2)
     - Sub-text (Small)

6. **Add charts section:**
   - 2 columns: 66% + 33%
   - Left: Revenue chart placeholder
   - Right: Activities feed

7. **Add tables:**
   - 2 equal columns
   - Left: Lease expirations table
   - Right: Pending approvals

### C. Buildings Page

**Frame Setup:**

1. Create frame: 1440 × 1024
2. Name: "Buildings - Grid"

**Layout:**

```
Frame (1440 × 1024)
├── Sidebar (same as dashboard)
│
└── Main Content
    ├── Header (same as dashboard)
    │
    ├── Toolbar (56px height)
    │   ├── Left: Add button, View toggle
    │   └── Right: Search, Filters
    │
    └── Grid Container
        ├── Building Cards (3 columns)
        └── Pagination
```

**Building Card Component:**

Create a card with:
- Size: ~360px width, auto height
- Building icon placeholder
- Building name (H3)
- Address (Caption with location icon)
- Stats grid (4 rows)
- Status badge
- Action buttons

---

## 5. Prototyping

### A. Create Interactions

1. **Select source frame** (e.g., Login button)
2. Right sidebar → Prototype tab
3. Click `+` next to "Interactions"
4. Set:
   - Trigger: On click
   - Action: Navigate to
   - Destination: Dashboard frame
   - Animation: Smart animate
   - Easing: Ease out
   - Duration: 300ms

### B. Add Hover States

For buttons and cards:

1. Create hover variant
2. Prototype tab
3. Add interaction:
   - Trigger: While hovering
   - Action: Change to
   - Destination: Hover variant
   - Animation: Smart animate

### C. Create Flow

Connect these screens:
```
Login → Dashboard → Buildings → (Back to Dashboard via sidebar)
```

### D. Present Prototype

1. Click ▶️ (Play button) top right
2. Or press `Shift + Space`
3. Share link: Click "Share" → "Copy link"

---

## 6. Export & Handoff

### A. Dev Mode

1. Toggle to Dev Mode (top right)
2. Developers can:
   - Inspect spacing
   - Copy CSS
   - Download assets
   - View specs

### B. Export Assets

1. Select element
2. Right sidebar → Export
3. Choose format:
   - SVG (for icons, logos)
   - PNG (for images)
   - PDF (for presentations)
4. Set scale: 1x, 2x, 3x

### C. Design Tokens

Export as JSON:

1. Install "Design Tokens" plugin
2. Select all color/text styles
3. Export as JSON
4. Use in code

### D. Component Library

Share with team:

1. Publish as team library
2. Right-click page → "Publish library"
3. Team can now use components in other files

---

## 📚 Useful Figma Plugins

Install these to speed up work:

1. **Iconify** - 100,000+ icons
2. **Unsplash** - Free stock photos
3. **Content Reel** - Generate fake data
4. **Autoflow** - Draw flow arrows
5. **Stark** - Accessibility checker
6. **Design Lint** - Check for consistency

---

## ⌨️ Essential Keyboard Shortcuts

```
TOOLS
F - Frame
R - Rectangle
T - Text
O - Ellipse
L - Line
P - Pen

LAYOUT
Shift + A - Auto layout
Ctrl/Cmd + G - Group
Ctrl/Cmd + Shift + G - Ungroup

COMPONENTS
Ctrl/Cmd + Alt + K - Create component
Ctrl/Cmd + Alt + B - Create component set

VIEW
Ctrl/Cmd + \ - Show/hide UI
Ctrl/Cmd + Y - Show outlines
Space + drag - Pan
Ctrl/Cmd + scroll - Zoom

ALIGN
Alt + H - Horizontal center
Alt + V - Vertical center
Alt + A - Align left
Alt + D - Align right
```

---

## 🎯 Pro Tips

1. **Use Auto Layout everywhere** - Makes responsive design easy
2. **Name layers properly** - Use "/" for hierarchy (e.g., "Button/Primary")
3. **Use components** - Don't repeat yourself
4. **Maintain 8px grid** - Align everything to grid
5. **Use styles** - Colors, text, effects
6. **Organize with pages** - Design system, components, screens
7. **Comment frequently** - Use `C` to add comments for team
8. **Version history** - File → "Show version history"

---

## 📋 Checklist

Before sharing:

- [ ] All colors use styles
- [ ] All text uses styles
- [ ] Components are properly named
- [ ] Pages are organized
- [ ] Prototype flows work
- [ ] Responsive breakpoints tested
- [ ] Accessibility checked (contrast, font sizes)
- [ ] Comments added for developers
- [ ] Assets exported
- [ ] Library published (if team project)

---

## 🆘 Common Issues

**Issue**: Font not showing
- **Fix**: Install Inter font on your system

**Issue**: Auto layout not working
- **Fix**: Select all elements → Shift + A

**Issue**: Components not updating
- **Fix**: Right-click component → "Push updates to instances"

**Issue**: Prototype not working
- **Fix**: Check connections in Prototype tab

---

## 📖 Learning Resources

- [Figma Tutorial (Official)](https://www.figma.com/resources/learn-design/)
- [Figma YouTube Channel](https://www.youtube.com/c/Figmadesign)
- [Config 2023 - Design Systems](https://config.figma.com/)
- [Figma Community Files](https://www.figma.com/community)

---

## 🎨 Design Handoff Checklist

When handing off to developers:

1. **Export design specs:**
   - Spacing measurements
   - Color values
   - Font sizes
   - Border radius values

2. **Provide assets:**
   - Icons (SVG)
   - Images (PNG/JPG)
   - Logos (SVG)

3. **Document components:**
   - Component states
   - Variant properties
   - Interactions

4. **Share prototype:**
   - Working prototype link
   - User flows documented
   - Edge cases noted

5. **Design tokens:**
   - JSON export
   - CSS variables
   - Tailwind config

---

**Need help?** The HTML prototypes in this folder show the exact implementation of these designs. Use them as reference while building in Figma!
