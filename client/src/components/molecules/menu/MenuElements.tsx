interface MenuProps {
    className: string
    anchor: {
        children: React.ReactNode
    }[]
}
const MenuElements = ({ className, anchor }: MenuProps) => {
    return (
        <ul className={`navbar-nav ${className}`}>
            {anchor && (
                anchor.map((obj, index) => (
                    <li
                        key={index}
                        className="nav-item"
                    >
                        {obj.children}
                    </li>
                ))
            )}
        </ul>
    );
};

export default MenuElements;