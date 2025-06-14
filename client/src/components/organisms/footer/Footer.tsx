import FooterElements from "../../molecules/footer/FooterElements";
import 'bootstrap/dist/css/bootstrap.min.css'

const Footer = () => {
    return (
        <footer className="text-ligt">
            <div className="container">
                <div className="row">
                    <FooterElements
                        className='col-md-2 col-lg-2 col-xl-2 mx-auto'
                        title='Giày'
                        anchor={[
                            {
                                'href': '#nike',
                                'name': 'Nike'
                            },
                            {
                                'href': '#adidas',
                                'name': 'Adidas'
                            },
                            {
                                'href': '#moiNhat',
                                'name': 'Mới nhất'
                            },
                            {
                                'href': '#tatCa',
                                'name': 'Tất cả'
                            },
                        ]}
                    />

                    <FooterElements
                        className='col-md-3 col-lg-3 col-xl-3 mx-auto'
                        title='LIÊN HỆ VỚI SHOP'
                        anchor={[
                            {
                                'href': '',
                                'name': 'Hướng dẫn đặt hàng'
                            },
                            {
                                'href': '',
                                'name': 'Thông tin thanh toán'
                            },
                            {
                                'href': '',
                                'name': 'Chính sách giao hàng và nhận hàng'
                            },
                            {
                                'href': '',
                                'name': 'Liên hệ'
                            },
                        ]}
                    />

                    <FooterElements
                        className='col-md-3 col-lg-3 col-xl-3 mx-auto'
                        title='LIÊN HỆ VỚI SHOP'
                        anchor={[
                            {
                                'href': '',
                                'name': 'Hướng dẫn đặt hàng'
                            },
                            {
                                'href': '',
                                'name': 'Thông tin thanh toán'
                            },
                            {
                                'href': '',
                                'name': 'Chính sách giao hàng và nhận hàng'
                            },
                            {
                                'href': '',
                                'name': 'Liên hệ'
                            },
                        ]}
                    />

                    <FooterElements
                        className='col-md-4 col-lg-4 col-xl-4 mx-auto'
                        title='THÔNG TIN FANPAGE'
                        anchor={[]}
                    />

                    <div className="col-md-3 col-lg-3 col-xl-3">
                        <h5>THÔNG TIN SHOP</h5>
                        <hr className="bg-white mb-2 mt-0 d-inline-block mx-auto w-25" />
                        <ul className="list-unstyled">
                            <li><i className="fa fa-home mr-2">Thạch Thất, Hòa Lạc</i></li>
                            <li><i className="fa fa-envelope mr-2"></i>Trần Xuân Hoàn</li>
                            <li><i className="fa fa-phone mr-2"></i>0949268271</li>
                        </ul>
                    </div>
                    <div className="col-12 copyright mt-3">
                        <p className="float-left">
                            <a href="#">Trở lại trang đầu</a>
                        </p>
                    </div>
                    <div className="col text-center border-top">
                        <strong>Nhom &copy;2024. All rights reserved.</strong>
                    </div>
                </div>
            </div>
        </footer>
    );
};

export default Footer;