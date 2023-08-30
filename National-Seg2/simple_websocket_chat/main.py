import asyncio

import websockets


CLIENTS = set()


async def handler(websocket):
  CLIENTS.add(websocket)
  try:
    async for message in websocket:
      websockets.broadcast(CLIENTS, message)
  finally:
    CLIENTS.remove(websocket)

async def main():
  async with websockets.serve(handler, "localhost", 8765):
    await asyncio.Future()


if __name__ == "__main__":
  asyncio.run(main())
