export interface RelaysState {
  relays?: (RelaysEntity)[] | null;
}
export interface RelaysEntity {
  relay: number;
  state: number;
  stateAfterRestart: number;
  name: string;
}
