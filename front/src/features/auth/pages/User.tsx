import {useParams} from "react-router-dom";

function User() {
    const {userId} = useParams();
    return (
        <>
            <p>{userId}</p>
        </>
    )
}
export default User;