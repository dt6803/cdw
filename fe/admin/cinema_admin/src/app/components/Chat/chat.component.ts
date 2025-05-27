import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Account } from 'src/app/models/account.model';
import { Chat, ListUser } from 'src/app/models/chat.model';


import { ChatService } from 'src/app/services/chatService.service';


@Component({
  selector: 'app-root',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit {
  messages: Chat[];

  newMessage: string = '';
  account: Account;
  listUser: ListUser[];
  username: string;
  constructor(private chatService: ChatService,
    private datePipe: DatePipe
    ) {}
  
  toDay: Date = new Date();
  ngOnInit(): void {
    this.chatService.listUser().then(
      res => {
        this.listUser = res as ListUser[];
        this.chatService.findChatByAccountId(this.listUser[0].accountId).then(
          res => {
            this.messages = res as Chat[];
            this.username = this.messages[0].name;
            console.log(res);
          }
        );
        console.log(this.listUser);
      }
    );
   
  
    this.chatService.onMessage().subscribe({
      next: (message: any) => {
        var chat: Chat = {
          message:  message.text ,
          role: 1,
          time: null,
          accountId: null,
          name: null
        };
        this.messages.push(chat);
      
      },
      error: (error: any) => {
        console.error('WebSocket error', error);
      },
      complete: () => {
        console.log('WebSocket connection closed');
      }
    });
  }

  sendMessage() {
    if (this.newMessage.trim()) {
      this.chatService.sendMessage(this.newMessage);
      var chat = {
        message:  this.newMessage,
        role: 0,
        time: this.datePipe.transform(this.toDay, 'dd/MM/yyyy HH:mm:ss'),
        accountId: 1,
        name: "null"
      };
      this.messages.push(chat);
      this.chatService.newChat(chat).then(
        res => {
          console.log(res);
        }
      );
      this.newMessage = '';
    }
  }
  changeChat(accountId: number){
    this.chatService.findChatByAccountId(accountId).then(
      res => {
        this.messages = res as Chat[];
        this.username = this.messages[0].name;
        console.log(res);
      }
    );
  }
}
