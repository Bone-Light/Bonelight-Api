import {Link} from "react-router-dom";
import {Button, Col, Row} from "antd";

function HomeIndex(){
    return (
        <>
            <h1>WelCome to LightApi</h1>
            <Row gutter={8} style={{textAlign: "center", margin: "auto"}}>
                <Col span={8}>
                    <Button>
                        <Link to='A'>前往A</Link>
                    </Button>
                </Col>
                <Col span={8}>
                    <Button>
                        <Link to='B'>前往B</Link>
                    </Button>
                </Col>
                <Col span={8}>
                    <Button>
                        <Link to='C'>前往C</Link>
                    </Button>
                </Col>
            </Row>
        </>
    )
}
export default HomeIndex;