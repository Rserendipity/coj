import httpInstance from "@/utils/httpInstance";

export function getAboutAPI(info) {
  return httpInstance.get(`/about/${info}`);
}