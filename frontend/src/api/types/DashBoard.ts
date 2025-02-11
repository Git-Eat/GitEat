export type CommitsResponse = {
  totalCommit: number;
};

type Participant = {
  userId: number;
  name: string;
  userName: string;
  avatarUrl: string;
};
export type Participants = {
  participants: Participant[];
};

export type PRStatistics = {
  totalMergeRequest: number;
  userList: (Participant & { mergeRequestCount: number })[];
};

export type CommentStatistics = {
  totalComment: number;
  userList: (Participant & { commentCount: number })[];
};
