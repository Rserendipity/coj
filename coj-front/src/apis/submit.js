import httpInstance from "@/utils/httpInstance";

export function getSubmitListAPI(userId, problemId) {
    return httpInstance.get(`/submit/get_detail_list?userId=${userId}&problemId=${problemId}`);
}

export function getSubmitDetailAPI(id) {
    return httpInstance.get(`/submit/get_detail?id=${id}`);
}

export function uploadSubmitAPI(body) {
    return httpInstance.post(`/submit/upload`, body);
}