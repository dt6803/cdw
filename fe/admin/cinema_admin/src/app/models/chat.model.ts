export class Chat{

    accountId: number;
    message: string;
    role: number;
    time: string;
    name: string;
}
export class ListUser{
    accountId: number;
    name: string;
    message: Chat[];

}