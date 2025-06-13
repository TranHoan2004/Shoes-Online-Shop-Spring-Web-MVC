interface FooterProps {
    className: string
    title: string
    anchor: {
        'href': string,
        'name': string
    }[]
}

const FooterElements = ({ className, title, anchor }: FooterProps) => {
    return (
        <div className={className}>
            <h5>{title}</h5>
            <hr className="bg-white mb-2 mt-0 d-inline-block mx-auto w-25" />
            <ul className="list-unstyled">
                {anchor && (
                    anchor.map((obj, index) => (
                        <li key={index}><a href={obj.href}>{obj.name}</a></li>
                    ))
                )}
            </ul>
        </div>
    );
};

export default FooterElements;