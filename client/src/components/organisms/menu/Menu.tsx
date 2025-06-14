import { Button } from "@heroui/react";
import 'bootstrap/dist/css/bootstrap.min.css'
import MenuElements from "../../molecules/menu/MenuElements";

const Menu = () => {
    return (
        <nav className="navbar navbar-expand-md navbar-dark bg-dark">
            <div className="container">
                <a className="navbar-brand" href="home">Siêu Thị Giày Trần Hoàn</a>
                <Button
                    className="navbar-toggler"
                    type="button"
                >
                    <span className="navbar-toggler-icon"></span>
                </Button>

                <div
                    className="collapse navbar-collapse justify-content-end"
                    id="navbarsExampleDefault"
                >
                    <MenuElements
                        className="m-auto"
                        anchor={[
                            {
                                'href': 'manageraccount',
                                'title': 'Quản lý tài khoản'
                            },
                            {
                                'href': 'managerproduct',
                                'title': 'Quản lý sản phẩm'
                            },
                            {
                                'href': '#',
                                'title': 'Xin chào Hoan'
                            }
                        ]}
                    />

                    <a className="btn btn-success btn-sm ml-3" href="managerCart">
                        <i className="fa fa-shopping-cart"></i>
                        <span className="cart">Giỏ hàng</span>
                        <b>
                            <span id="amountCart"
                                className="badge badge-light"
                            ></span>
                        </b>
                    </a>

                    <MenuElements
                        className=""
                        anchor={[
                            {
                                'href': 'logout',
                                'title': 'Đăng xuất'
                                React.ReactNode(<a class="nav-link" href="logout">Đăng xuất</a>)
                            },
                            {
                                'href': 'logout',
                                'title': 'Đăng xuất'
                            },
                        ]}
                    />
                </div>
            </div>
        </nav>
    );
};

export default Menu;