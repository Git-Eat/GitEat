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

export type Contributors = {
  contributors: [
    {
      userId: number;
      name: string;
      userName: string;
      avatarUrl: string;
      totalCommit: number;
      totalMergeRequest: number;
      totalComment: number;
      weeklyInfo: [
        {
          week: number;
          mergeRequestCount: number;
          commitCount: number;
          commentCount: number;
        },
      ];
    },
  ];
};
